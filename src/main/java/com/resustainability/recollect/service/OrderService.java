package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.*;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddOrderRequest;
import com.resustainability.recollect.dto.request.CancelOrderRequest;
import com.resustainability.recollect.dto.request.PlaceOrderRequest;
import com.resustainability.recollect.dto.request.UpdateOrderScheduleDateRequest;
import com.resustainability.recollect.dto.response.*;
import com.resustainability.recollect.entity.backend.BioWasteOrders;

import com.resustainability.recollect.entity.backend.*;
import com.resustainability.recollect.exception.InvalidDataException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.exception.UnauthorizedException;
import com.resustainability.recollect.repository.*;
import com.resustainability.recollect.tag.OrderStatus;
import com.resustainability.recollect.tag.OrderType;
import com.resustainability.recollect.tag.Role;
import com.resustainability.recollect.commons.OrderLogUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final SecurityService securityService;
    private final CustomerAddressService customerAddressService;
    private final ScrapRegionAvailabilityService scrapRegionAvailabilityService;
    private final LocalBodyAvailabilityService localBodyAvailabilityService;
    private final MobileService mobileService;
    private final PushNotificationService pushNotificationService;

    private final CustomerAddressRepository customerAddressRepository;
    private final OrderCancelReasonRepository orderCancelReasonRepository;
    private final CompleteOrdersRepository completeOrdersRepository;
    private final CompleteOrderLogRepository completeOrderLogRepository;
    private final BioWasteOrderCartRepository bioWasteOrderCartRepository;
    private final ScrapOrderCartRepository scrapOrderCartRepository;
    private final ScrapOrdersRepository scrapOrdersRepository;
    private final BioWasteOrdersRepository bioWasteOrdersRepository;
    private final ScrapRegionRepository scrapRegionRepository;
    private final ScrapTypeRepository scrapTypeRepository;
    private final BioWasteTypeRepository bioWasteTypeRepository;
    private final WardRepository wardRepository;
    private final DistrictRepository districtRepository;
    private final StateRepository stateRepository;
    private final CustomerRepository customerRepository;
    private final ProviderRepository providerRepository;
    private final ProviderDistrictRepository providerDistrictRepository;
    

    @Autowired
    public OrderService(
            SecurityService securityService,
            CustomerAddressService customerAddressService,
            ScrapRegionAvailabilityService scrapRegionAvailabilityService,
            LocalBodyAvailabilityService localBodyAvailabilityService,
            MobileService mobileService,
            PushNotificationService pushNotificationService,
            CustomerAddressRepository customerAddressRepository,
            OrderCancelReasonRepository orderCancelReasonRepository,
            CompleteOrdersRepository completeOrdersRepository,
            CompleteOrderLogRepository completeOrderLogRepository,
            BioWasteOrderCartRepository bioWasteOrderCartRepository,
            ScrapOrderCartRepository scrapOrderCartRepository,
            ScrapOrdersRepository scrapOrdersRepository,
            BioWasteOrdersRepository bioWasteOrdersRepository,
            ScrapRegionRepository scrapRegionRepository,
            ScrapTypeRepository scrapTypeRepository,
            BioWasteTypeRepository bioWasteTypeRepository,
            WardRepository wardRepository,
            DistrictRepository districtRepository,
            StateRepository stateRepository,
            CustomerRepository customerRepository,
            ProviderRepository providerRepository,
            ProviderDistrictRepository providerDistrictRepository
    ) {
        this.securityService = securityService;
        this.customerAddressService = customerAddressService;
        this.scrapRegionAvailabilityService = scrapRegionAvailabilityService;
        this.localBodyAvailabilityService = localBodyAvailabilityService;
        this.mobileService = mobileService;
        this.pushNotificationService = pushNotificationService;
        this.customerAddressRepository = customerAddressRepository;
        this.orderCancelReasonRepository = orderCancelReasonRepository;
        this.completeOrdersRepository = completeOrdersRepository;
        this.completeOrderLogRepository = completeOrderLogRepository;
        this.bioWasteOrderCartRepository = bioWasteOrderCartRepository;
        this.scrapOrderCartRepository = scrapOrderCartRepository;
        this.scrapOrdersRepository = scrapOrdersRepository;
        this.bioWasteOrdersRepository = bioWasteOrdersRepository;
        this.scrapRegionRepository = scrapRegionRepository;
        this.scrapTypeRepository = scrapTypeRepository;
        this.bioWasteTypeRepository = bioWasteTypeRepository;
        this.wardRepository = wardRepository;
        this.districtRepository = districtRepository;
        this.stateRepository = stateRepository;
        this.customerRepository = customerRepository;
        this.providerRepository = providerRepository;
        this.providerDistrictRepository = providerDistrictRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<IOrderCancelReasonResponse> listCancellationReasons() {
        return orderCancelReasonRepository.findAllOrderCancelReasons();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<EnumOrdinalResponse> listStatusesInOrdinals() {
        return Arrays.stream(OrderStatus.values())
                .map(value -> new EnumOrdinalResponse(
                        value.getOrdinal(),
                        value.name(),
                        value.getAbbreviation()
                ))
                .toList();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<IOrderCartItemResponse> listAllCartItems(Long completeOrderId) {
        return completeOrdersRepository.findAllCartItemsByOrderId(completeOrderId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<IOrderHistoryResponse> listHistory(
            Set<String> orderStatuses,
            OrderType orderType,
            SearchCriteria searchCriteria
    ) {
        final IUserContext user = securityService
                .getCurrentUser()
                .orElseThrow(UnauthorizedException::new);

        final Pageable pageable = searchCriteria.toPageRequest();

        if (Boolean.TRUE.equals(user.getIsAdmin())) {
            return Pager.of(
                    completeOrdersRepository.findAllPaged(
                            CollectionUtils.isBlank(orderStatuses) ? null : orderStatuses,
                            null == orderType ? null : orderType.getAbbreviation(),
                            searchCriteria.getQ(),
                            pageable
                    )
            );

        } else if (Boolean.TRUE.equals(user.getIsCustomer())) {
            return Pager.of(
                    completeOrdersRepository.findAllPagedIfBelongsToCustomer(
                            user.getId(),
                            CollectionUtils.isBlank(orderStatuses) ? null : orderStatuses,
                            null == orderType ? null : orderType.getAbbreviation(),
                            searchCriteria.getQ(),
                            pageable
                    )
            );

        } else if (Boolean.TRUE.equals(user.getIsProvider())) {
            return Pager.of(
                    completeOrdersRepository.findAllPagedIfBelongsToProvider(
                            user.getId(),
                            CollectionUtils.isBlank(orderStatuses) ? null : orderStatuses,
                            null == orderType ? null : orderType.getAbbreviation(),
                            searchCriteria.getQ(),
                            pageable
                    )
            );
        }

        return Pager.empty(pageable);
    }

    
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<IOrderHistoryResponse> listAssignable(SearchCriteria searchCriteria) {
        final IUserContext user = securityService
                .getCurrentUser()
                .orElseThrow(UnauthorizedException::new);

        final Pageable pageable = searchCriteria.toPageRequest();

        if (Boolean.TRUE.equals(user.getIsAdmin())) {
            return Pager.of(
                    completeOrdersRepository.findAllAssignablePaged(
                            OrderStatus.OPEN.getAbbreviation(),
                            searchCriteria.getQ(),
                            pageable
                    )
            );
        } else if (Boolean.TRUE.equals(user.getIsProvider())) {
            final Set<Long> districtIds = providerDistrictRepository
                    .listAllActiveProviderDistrictIds(user.getId());

            return Pager.of(
                    completeOrdersRepository.findAllAssignablePagedIfBelongs(
                            OrderStatus.OPEN.getAbbreviation(),
                            districtIds,
                            searchCriteria.getQ(),
                            pageable
                    )
            );
        }

        return Pager.empty(pageable);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<IOrderLogResponse> listTimeline(Long completeOrderId, SearchCriteria searchCriteria) {
        ValidationUtils.validateId(completeOrderId);
        return Pager.of(
                completeOrderLogRepository.findLogsByCompleteOrderId(
                        completeOrderId,
                        searchCriteria.getQ(),
                        searchCriteria.toPageRequest()
                )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IOrderHistoryResponse getById(Long completeOrderId) {
        ValidationUtils.validateId(completeOrderId);

        final IUserContext user = securityService
                .getCurrentUser()
                .orElseThrow(UnauthorizedException::new);

        if (Boolean.TRUE.equals(user.getIsCustomer())) {
            return completeOrdersRepository
                    .findByCompleteOrderIdIfBelongsToCustomer(user.getId(), completeOrderId)
                    .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER));
        }

        else if (Boolean.TRUE.equals(user.getIsAdmin()) || Boolean.TRUE.equals(user.getIsProvider())) {
            return completeOrdersRepository
                    .findByCompleteOrderId(completeOrderId)
                    .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER));
        }

        throw  new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public InvoiceResponse getInvoiceById(Long completeOrderId) {
        ValidationUtils.validateId(completeOrderId);

        return completeOrdersRepository
                .findInvoiceDetailsByOrderId(completeOrderId)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long placeOrder(PlaceOrderRequest request, OrderType orderType) {
        Objects.requireNonNull(orderType);
        ValidationUtils.validateRequestBody(request);

        final IUserContext user = securityService
                .getCurrentUser()
                .filter(usr -> Boolean.TRUE.equals(usr.getIsCustomer()))
                .orElseThrow(UnauthorizedException::new);

        final ICustomerAddressResponse address = customerAddressRepository
                .findByCustomerAddressIdIfBelongs(user.getId(), request.addressId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_CUSTOMER_ADDRESS));

        final Customer customer = customerRepository
                .getReferenceById(user.getId());

        final CustomerAddress customerAddress = customerAddressRepository
                .getReferenceById(request.addressId());

        final District district = null != address.getDistrictId()
                ? districtRepository.getReferenceById(address.getDistrictId())
                : null;
        final State state = null != address.getStateId()
                ? stateRepository.getReferenceById(address.getStateId())
                : null;

        final double defaultDoubleValue = 0.0d;
        final String defaultOrderStatus = OrderStatus.OPEN.getAbbreviation();

        final ScrapOrders scrapOrder;
        final BioWasteOrders bioWasteOrder;
        if (OrderType.SCRAP.equals(orderType)) {
            if (null == address.getScrapRegionId()) {
                throw new InvalidDataException("Scrap region is required, Set region in your address.");
            }

            if (!customerAddressService.isInScrapRegionBoundaries(
                    address.getLatitude(),
                    address.getLongitude(),
                    address.getScrapRegionId()
            )) {
                throw new InvalidDataException(
                        "Address is outside the selected scrap region service area"
                );
            }

            final ScrapRegion scrapRegion = scrapRegionRepository
                    .getReferenceById(address.getScrapRegionId());

            if (!scrapRegionAvailabilityService.bookSlot(address.getScrapRegionId(), request.scheduleDate())) {
                throw new InvalidDataException(
                        String.format(
                                "Booking slot full for date %s, choose another available date.",
                                DateTimeFormatUtils.toDateShortText(request.scheduleDate())
                        )
                );
            }

            scrapOrder = scrapOrdersRepository.save(
                    new ScrapOrders(
                            null,
                            IdGenerator.nextId(),
                            LocalDateTime.now(),
                            request.scheduleDate(),
                            request.altNumber(),
                            null,
                            null,
                            null,
                            defaultDoubleValue,
                            defaultOrderStatus,
                            request.platform(),
                            false,
                            customerAddress,
                            null,
                            scrapRegion,
                            state,
                            customer,
                            0
                    )
            );
            bioWasteOrder = null;

            final Map<Long, ItemCategoryTypeResponse> indexedTypes = mobileService
                    .listScrapCategories(
                            null == district ? user.getDistrictId() : district.getId()
                    )
                    .stream()
                    .map(ItemCategoryResponse::types)
                    .filter(Objects::nonNull)
                    .flatMap(List::stream)
                    .collect(Collectors.toMap(
                            ItemCategoryTypeResponse::id,
                            Function.identity()
                    ));

            final List<ScrapOrderCart> orderItems = request
                    .items()
                    .stream()
                    .filter(item -> null != item.id() && null != item.quantity() && item.quantity() > 0 && indexedTypes.containsKey(item.id()))
                    .map(item -> {
                        final ItemCategoryTypeResponse type = indexedTypes.get(item.id());
                        final double quantity = type.kg()
                                ? item.quantity()
                                : 0;
                        final double price = null != type.price() && type.price() > 0
                                ? type.price()
                                : 0;
                        return new ScrapOrderCart(
                                null,
                                quantity,
                                price,
                                type.kg() ? (price * quantity) : price,
                                false,
                                scrapOrder,
                                scrapTypeRepository.getReferenceById(type.id())
                        );
                    })
                    .toList();
            if (CollectionUtils.isNonBlank(orderItems)) {
                scrapOrderCartRepository.saveAll(orderItems);
            }
        } else if (OrderType.BIO_WASTE.equals(orderType)) {
            if (null == address.getWardId()) {
                throw new InvalidDataException("Ward is required, Set ward in your address.");
            }

            if (!customerAddressService.isInLocalBodyBoundaries(
                    address.getLatitude(),
                    address.getLongitude(),
                    address.getLocalBodyId()
            )) {
                throw new InvalidDataException(
                        "Address is outside the selected localbody/ward region service area"
                );
            }

            final Ward ward = wardRepository
                    .getReferenceById(address.getWardId());

            if (!localBodyAvailabilityService.bookSlot(address.getLocalBodyId(), request.scheduleDate())) {
                throw new InvalidDataException(
                        String.format(
                                "Booking slot full for date %s, choose another available date.",
                                DateTimeFormatUtils.toDateShortText(request.scheduleDate())
                        )
                );
            }

            bioWasteOrder = bioWasteOrdersRepository.save(
                    new BioWasteOrders(
                            null,
                            IdGenerator.nextId(),
                            LocalDateTime.now(),
                            request.scheduleDate(),
                            defaultDoubleValue,
                            request.altNumber(),
                            null,
                            null,
                            null,
                            defaultOrderStatus,
                            request.platform(),
                            false,
                            customerAddress,
                            null,
                            state,
                            ward,
                            customer,
                            0,
                            defaultDoubleValue,
                            null,
                            defaultDoubleValue
                    )
            );
            scrapOrder = null;

            final Set<Long> indexedTypes = mobileService
                    .listBioWasteCategories(address.getLocalBodyId())
                    .stream()
                    .map(ItemCategoryResponse::types)
                    .filter(Objects::nonNull)
                    .flatMap(List::stream)
                    .map(ItemCategoryTypeResponse::id)
                    .collect(Collectors.toUnmodifiableSet());

            final List<BioWasteOrderCart> orderItems = request
                    .items()
                    .stream()
                    .filter(item -> null != item.id() && indexedTypes.contains(item.id()))
                    .map(item -> new BioWasteOrderCart(
                            null,
                            false,
                            bioWasteOrder,
                            bioWasteTypeRepository.getReferenceById(item.id())
                    ))
                    .toList();
            if (CollectionUtils.isNonBlank(orderItems)) {
                bioWasteOrderCartRepository.saveAll(orderItems);
            }
        } else {
            throw new InvalidDataException("Specify supported order type");
        }

        final CompleteOrders completeOrder = completeOrdersRepository.save(
                new CompleteOrders(
                        null,
                        request.scheduleDate(),
                        orderType.getAbbreviation(),
                        defaultOrderStatus,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        defaultDoubleValue,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        false,
                        null,
                        false,
                        false,
                        bioWasteOrder,
                        null,
                        null,
                        district,
                        null,
                        null,
                        scrapOrder,
                        state,
                        customer,
                        null
                )
        );

        final String userRole = Role.fromUserContext(user);

        completeOrderLogRepository.save(
                new CompleteOrderLog(
                        null,
                        toFormattedDoneBy(user, userRole),
                        String.format(
                                "Order Placed with Schedule Date %s by ",
                                DateTimeFormatUtils.toIsoDate(request.scheduleDate())
                        ),
                        LocalDateTime.now(),
                        null,
                        null,
                        completeOrder,
                        null,
                        customer
                )
        );

        return completeOrder.getId();
    }
    
   

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void updateScheduledDate(UpdateOrderScheduleDateRequest request) {
        ValidationUtils.validateRequestBody(request);

        final IUserContext user = securityService
                .getCurrentUser()
                .orElseThrow(UnauthorizedException::new);

        final CompleteOrders completeOrder = completeOrdersRepository
                .findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER));

        if (request.scheduleDate().equals(completeOrder.getScheduleDate())) {
        	throw new InvalidDataException(Default.ERROR_ORDER_RESCHEDULE_SAME_DATE);
        }

        if (0 == completeOrdersRepository.updateScheduledDate(
                request.id(),
                request.scheduleDate()
        )) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER);
        }

        final CompleteOrderLog log = new CompleteOrderLog();
        log.setOrder(completeOrder);
        log.setCustomer(completeOrder.getCustomer());
        log.setDoneBy(OrderLogUtils.resolveDoneBy(user));
        log.setCreatedAt(LocalDateTime.now());

        log.setDescription(
                String.format(
                        "Order Re-Schedule Date Updated to %s and Order Status is %s by ",
                        DateTimeFormatUtils.toIsoDate(request.scheduleDate()),
                        completeOrder.getOrderStatus()
                )
        );

        completeOrderLogRepository.save(log);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long selfAssign(Long completeOrderId) {
        ValidationUtils.validateId(completeOrderId);

        final IUserContext user = securityService
                .getCurrentUser()
                .filter(usr -> Boolean.TRUE.equals(usr.getIsProvider()))
                .orElseThrow(UnauthorizedException::new);

        final IOrderHistoryResponse order = completeOrdersRepository
                .findByCompleteOrderId(completeOrderId)
                .filter(ord -> StringUtils.isNotBlank(ord.getType()))
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER));

            final Set<Long> districtIds = providerDistrictRepository
                .listAllActiveProviderDistrictIds(user.getId());

        if (!OrderStatus.is(order.getStatus(), OrderStatus.OPEN)) {
            throw new InvalidDataException("This order is no longer available for assignment.");
        }

        if (0 == completeOrdersRepository.assignProviderIfEligible(
                completeOrderId,
                user.getId(),
                districtIds,
                OrderStatus.OPEN.getAbbreviation(),
                OrderStatus.CONFIRMED.getAbbreviation()
        )) {
            throw new InvalidDataException("This order has already been claimed and cannot be assigned to you.");
        }

        final LocalDateTime now = LocalDateTime.now();
        final String userRole = Role.fromUserContext(user);

        final CompleteOrderLog orderLog = new CompleteOrderLog(
                null,
                toFormattedDoneBy(user, userRole),
                String.format(
                        "Order Accepted on %s by ",
                        DateTimeFormatUtils.toDbTimestamp(now)
                ),
                now,
                null,
                null,
                completeOrdersRepository.getReferenceById(order.getId()),
                providerRepository.getReferenceById(user.getId()),
                null != order.getCustomerId() ? customerRepository.getReferenceById(order.getCustomerId()) : null
        );
        completeOrderLogRepository.save(orderLog);

        pushNotificationService.sendToCustomer(
                order.getCustomerId(),
                Default.SUCCESS_NOTIFICATION_TITLE,
                (orderLog.getDescription() + orderLog.getDoneBy()),
                null
        );

        return order.getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void logEventHeadedToAddress(Long completeOrderId) {
        ValidationUtils.validateId(completeOrderId);

        final IUserContext user = securityService
                .getCurrentUser()
                .filter(usr -> Boolean.TRUE.equals(usr.getIsProvider()))
                .orElseThrow(UnauthorizedException::new);

        final IOrderHistoryResponse order = completeOrdersRepository
                .findByCompleteOrderIdIfBelongsToProvider(user.getId(), completeOrderId)
                .filter(ord -> null != ord.getType())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER));

        if (OrderStatus.is(order.getStatus(), OrderStatus.COMPLETED, OrderStatus.CANCELLED)) {
            throw new InvalidDataException(
                    String.format("Once an order is %s, it can no longer be marked as headed to pickup.", order.getStatus())
            );
        }

        final String orderStatus = OrderStatus.PENDING.getAbbreviation();

        if (OrderType.is(order.getType(), OrderType.SCRAP)) {
            if (0 == scrapOrdersRepository.updateStatusByScrapOrderId(
                    order.getScrapOrderId(),
                    orderStatus
            )) {
                throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER);
            }
        }

        if (OrderType.is(order.getType(), OrderType.BIO_WASTE)) {
            if (0 == bioWasteOrdersRepository.updateStatusByBioWasteOrderId(
                    order.getBioWasteOrderId(),
                    orderStatus
            )) {
                throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER);
            }
        }

        if (0 == completeOrdersRepository.updateStatusByCompleteOrderId(
                completeOrderId,
                orderStatus
        )) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER);
        }

        final LocalDateTime now = LocalDateTime.now();
        final String userRole = Role.fromUserContext(user);

        completeOrderLogRepository.save(
                new CompleteOrderLog(
                        null,
                        toFormattedDoneBy(user, userRole),
                        String.format(
                                "Started Travelling to address on %s by ",
                                DateTimeFormatUtils.toDbTimestamp(now)
                        ),
                        now,
                        null,
                        null,
                        completeOrdersRepository.getReferenceById(order.getId()),
                        providerRepository.getReferenceById(user.getId()),
                        null != order.getCustomerId() ? customerRepository.getReferenceById(order.getCustomerId()) : null
                )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void logEventReachedToAddress(Long completeOrderId) {
        ValidationUtils.validateId(completeOrderId);

        final IUserContext user = securityService
                .getCurrentUser()
                .filter(usr -> Boolean.TRUE.equals(usr.getIsProvider()))
                .orElseThrow(UnauthorizedException::new);

        final IOrderHistoryResponse order = completeOrdersRepository
                .findByCompleteOrderIdIfBelongsToProvider(user.getId(), completeOrderId)
                .filter(ord -> null != ord.getType())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER));

        if (OrderStatus.is(order.getStatus(), OrderStatus.COMPLETED, OrderStatus.CANCELLED)) {
            throw new InvalidDataException(
                    String.format("Once an order is %s, it can no longer be marked as headed to pickup.", order.getStatus())
            );
        }

        final String orderStatus = OrderStatus.PENDING.getAbbreviation();

        if (OrderType.is(order.getType(), OrderType.SCRAP)) {
            if (0 == scrapOrdersRepository.updateStatusByScrapOrderId(
                    order.getScrapOrderId(),
                    orderStatus
            )) {
                throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER);
            }
        }

        if (OrderType.is(order.getType(), OrderType.BIO_WASTE)) {
            if (0 == bioWasteOrdersRepository.updateStatusByBioWasteOrderId(
                    order.getBioWasteOrderId(),
                    orderStatus
            )) {
                throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER);
            }
        }

        if (0 == completeOrdersRepository.updateStatusByCompleteOrderId(
                completeOrderId,
                orderStatus
        )) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER);
        }

        final LocalDateTime now = LocalDateTime.now();
        final String userRole = Role.fromUserContext(user);

        completeOrderLogRepository.save(
                new CompleteOrderLog(
                        null,
                        toFormattedDoneBy(user, userRole),
                        String.format(
                                "Reached address location on %s by ",
                                DateTimeFormatUtils.toDbTimestamp(now)
                        ),
                        now,
                        null,
                        null,
                        completeOrdersRepository.getReferenceById(order.getId()),
                        providerRepository.getReferenceById(user.getId()),
                        null != order.getCustomerId() ? customerRepository.getReferenceById(order.getCustomerId()) : null
                )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void cancelById(CancelOrderRequest request) {
        ValidationUtils.validateRequestBody(request);

        final IOrderHistoryResponse order = completeOrdersRepository
                .findByCompleteOrderId(request.id())
                .filter(ord -> null != ord.getType())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER));

        if (OrderStatus.is(order.getStatus(), OrderStatus.COMPLETED)) {
            throw new InvalidDataException("Once an order is completed, it can no longer be cancelled.");
        }

        if (!orderCancelReasonRepository.existsById(request.reasonId())) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_REASON);
        }

        final String orderStatus = OrderStatus.CANCELLED.getAbbreviation();

        if (OrderType.is(order.getType(), OrderType.SCRAP)) {
            if (0 == scrapOrdersRepository.cancelByScrapOrderId(
                    order.getScrapOrderId(),
                    request.reasonId(),
                    orderStatus
            )) {
                throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER);
            }

            scrapRegionAvailabilityService
                    .freeSlot(order.getScrapRegionId(), order.getScheduleDate());
        }

        if (OrderType.is(order.getType(), OrderType.BIO_WASTE)) {
            if (0 == bioWasteOrdersRepository.cancelByBioWasteOrderId(
                    order.getBioWasteOrderId(),
                    request.reasonId(),
                    orderStatus
            )) {
                throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER);
            }

            localBodyAvailabilityService
                    .freeSlot(order.getLocalBodyId(), order.getScheduleDate());
        }

        if (0 == completeOrdersRepository.cancelByCompleteOrderId(
                request.id(),
                true,
                orderStatus

        )) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER);
        }

        // TODO - Log order cancellation in completeorderslog table
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void deleteById(Long completeOrderId, boolean value) {
        ValidationUtils.validateId(completeOrderId);

        final IOrderHistoryResponse order = completeOrdersRepository
                .findByCompleteOrderId(completeOrderId)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER));

        if (OrderType.is(order.getType(), OrderType.SCRAP) && 0 == scrapOrdersRepository.deleteByScrapOrderId(
                order.getScrapOrderId(),
                value
        )) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER);
        }

        if (OrderType.is(order.getType(), OrderType.BIO_WASTE) && 0 == bioWasteOrdersRepository.deleteByBioWasteOrderId(
                order.getBioWasteOrderId(),
                value
        )) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER);
        }

        if (0 == completeOrdersRepository.deleteByCompleteOrderId(
                completeOrderId,
                value
        )) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER);
        }
    }

    private String toFormattedDoneBy(IUserContext user, String userRole) {
        return String.format(
                "%s%s%s",
                user.getFullName(),
                StringUtils.isNotBlank(user.getPhoneNumber())
                        ? String.format("/%s", user.getPhoneNumber())
                        : Default.EMPTY,
                StringUtils.isNotBlank(userRole)
                        ? String.format(" - (%s)", userRole)
                        : Default.EMPTY
        );
    }
    
 

   /* @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddOrderRequest request, OrderType orderType) {

        Objects.requireNonNull(orderType, "Order type is required");
        ValidationUtils.validateRequestBody(request);

      
        final Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(Default.ERROR_NOT_FOUND_CUSTOMER)
                );

        
        final ICustomerAddressResponse addressResponse =
                customerAddressRepository.findByCustomerAddressIdIfBelongs(
                                request.customerId(),
                                Long.valueOf(request.customerAddressId())
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(Default.ERROR_NOT_FOUND_CUSTOMER_ADDRESS)
                        );

        final CustomerAddress customerAddress =
                customerAddressRepository.getReferenceById(addressResponse.getId());

        final District district = addressResponse.getDistrictId() != null
                ? districtRepository.getReferenceById(addressResponse.getDistrictId())
                : null;

        final State state = addressResponse.getStateId() != null
                ? stateRepository.getReferenceById(addressResponse.getStateId())
                : null;

        final double defaultDoubleValue = 0.0d;
        final String defaultOrderStatus = OrderStatus.OPEN.getAbbreviation();

        ScrapOrders scrapOrder = null;
        BioWasteOrders bioWasteOrder = null;

       
        if (OrderType.SCRAP.equals(orderType)) {

            if (addressResponse.getScrapRegionId() == null) {
                throw new InvalidDataException("Scrap region is required, set region in customer address");
            }

            if (!customerAddressService.isInScrapRegionBoundaries(
                    addressResponse.getLatitude(),
                    addressResponse.getLongitude(),
                    addressResponse.getScrapRegionId()
            )) {
                throw new InvalidDataException("Address is outside the scrap service area");
            }

            if (!scrapRegionAvailabilityService.bookSlot(
                    addressResponse.getScrapRegionId(),
                    request.scheduleDate()
            )) {
                throw new InvalidDataException(
                        String.format(
                                "Booking slot full for date %s",
                                DateTimeFormatUtils.toDateShortText(request.scheduleDate())
                        )
                );
            }

            final ScrapRegion scrapRegion =
                    scrapRegionRepository.getReferenceById(addressResponse.getScrapRegionId());
            
            ScrapOrders savedScrapOrder = scrapOrdersRepository.save(
                    new ScrapOrders(
                            null,
                            IdGenerator.nextId(),
                            LocalDateTime.now(),
                            request.scheduleDate(),
                            request.phoneNumber(),
                            null,
                            request.preferredPaymentMethod(),
                            request.comment(),
                            defaultDoubleValue,
                            defaultOrderStatus,
                            "ADMIN",
                            false,
                            customerAddress,
                            null,
                            scrapRegion,
                            customer.getState(),
                            customer,
                            1
                    )
            );

            
            scrapOrder = savedScrapOrder;

            List<ScrapOrderCart> carts = request.typeIds()
                    .stream()
                    .map(id -> scrapTypeRepository.findByIdAndIsActiveTrue(id)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException("Scrap type not found: " + id)
                            )
                    )
                    .map(type -> new ScrapOrderCart(
                            null,
                            0.0,
                            0.0,
                            0.0,
                            false,
                            savedScrapOrder,   
                            type
                    ))
                    .toList();

            scrapOrderCartRepository.saveAll(carts);
        }

        
        else if (OrderType.BIO_WASTE.equals(orderType)) {

            if (addressResponse.getWardId() == null) {
                throw new InvalidDataException("Ward is required, set ward in customer address");
            }

            if (!customerAddressService.isInLocalBodyBoundaries(
                    addressResponse.getLatitude(),
                    addressResponse.getLongitude(),
                    addressResponse.getLocalBodyId()
            )) {
                throw new InvalidDataException("Address is outside local body service area");
            }

            if (!localBodyAvailabilityService.bookSlot(
                    addressResponse.getLocalBodyId(),
                    request.scheduleDate()
            )) {
                throw new InvalidDataException(
                        String.format(
                                "Booking slot full for date %s",
                                DateTimeFormatUtils.toDateShortText(request.scheduleDate())
                        )
                );
            }

            final Ward ward =
                    wardRepository.getReferenceById(addressResponse.getWardId());

            BioWasteOrders savedBioWasteOrder = bioWasteOrdersRepository.save(
                    new BioWasteOrders(
                            null,
                            IdGenerator.nextId(),
                            LocalDateTime.now(),
                            request.scheduleDate(),
                            defaultDoubleValue,
                            request.phoneNumber(),
                            null,
                            request.preferredPaymentMethod(),
                            request.comment(),
                            defaultOrderStatus,
                            "ADMIN",
                            false,
                            customerAddress,
                            null,
                            state,
                            ward,
                            customer,
                            0,
                            defaultDoubleValue,
                            null,
                            defaultDoubleValue
                    )
            );

           
            bioWasteOrder = savedBioWasteOrder;

            List<BioWasteOrderCart> carts = request.typeIds()
                    .stream()
                    .map(id -> bioWasteTypeRepository.findById(id)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException("Biowaste type not found: " + id)
                            )
                    )
                    .map(type -> new BioWasteOrderCart(
                            null,
                            false,
                            savedBioWasteOrder, 
                            type
                    ))
                    .toList();

            bioWasteOrderCartRepository.saveAll(carts);

        }

        else {
            throw new InvalidDataException("Unsupported order type");
        }

        
        final CompleteOrders completeOrder = completeOrdersRepository.save(
                new CompleteOrders(
                        null,
                        request.scheduleDate(),
                        orderType.getAbbreviation(),
                        defaultOrderStatus,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        defaultDoubleValue,
                        null,
                        null,
                        null,
                        null,
                        null,
                        request.preferredPaymentMethod(),
                        false,
                        null,
                        false,
                        false,
                        bioWasteOrder,
                        null,
                        null,
                        district,
                        null,
                        null,
                        scrapOrder,
                        state,
                        customer,
                        null
                )
        );

        
        completeOrderLogRepository.save(
                new CompleteOrderLog(
                        null,
                        "ADMIN",
                        String.format(
                                "Order placed by admin with schedule date %s",
                                DateTimeFormatUtils.toIsoDate(request.scheduleDate())
                        ),
                        LocalDateTime.now(),
                        null,
                        null,
                        completeOrder,
                        null,
                        customer
                )
        );

        return completeOrder.getId();
    }*/

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddOrderRequest request, OrderType orderType) {

        Objects.requireNonNull(orderType, "Order type is required");
        ValidationUtils.validateRequestBody(request);

       
        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                Default.ERROR_NOT_FOUND_CUSTOMER
                        )
                );

        final double defaultDoubleValue = 0.0d;
        final String defaultOrderStatus =
                OrderStatus.OPEN.getAbbreviation();

        CustomerAddress customerAddress;
        District district = null;
        State state = customer.getState();

       

      
        if (request.customerAddressId() != null) {

            ICustomerAddressResponse addressResponse =
                    customerAddressRepository
                            .findByCustomerAddressIdIfBelongs(
                                    request.customerId(),
                                    request.customerAddressId()
                            )
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            Default.ERROR_NOT_FOUND_CUSTOMER_ADDRESS
                                    )
                            );

            customerAddress =
                    customerAddressRepository.getReferenceById(
                            addressResponse.getId()
                    );

            if (addressResponse.getDistrictId() != null) {
                district =
                        districtRepository.getReferenceById(
                                addressResponse.getDistrictId()
                        );
            }
        }

        
        else {

            if (OrderType.SCRAP.equals(orderType)) {

                if (request.scrapRegionId() == null) {
                    throw new InvalidDataException(
                            "Scrap region is required"
                    );
                }

                ScrapRegion scrapRegion =
                        scrapRegionRepository.getReferenceById(
                                request.scrapRegionId()
                        );

                customerAddress =
                        customerAddressRepository.save(
                                new CustomerAddress(
                                        null,
                                        true,
                                        true,
                                        false,
                                        false,
                                        request.addressType(),
                                        request.address(),
                                        request.landmark(),
                                        "0",
                                        "0",
                                        false,
                                        scrapRegion,
                                        null,
                                        customer
                                )
                        );
            }

            else if (OrderType.BIO_WASTE.equals(orderType)) {

                if (request.wardId() == null) {
                    throw new InvalidDataException(
                            "Ward is required"
                    );
                }

                Ward ward =
                        wardRepository.getReferenceById(
                                request.wardId()
                        );

                customerAddress =
                        customerAddressRepository.save(
                                new CustomerAddress(
                                        null,
                                        false,
                                        false,
                                        true,
                                        true,
                                        request.addressType(),
                                        request.address(),
                                        request.landmark(),
                                        "0",
                                        "0",
                                        false,
                                        null,
                                        ward,
                                        customer
                                )
                        );
            }

            else {
                throw new InvalidDataException(
                        "Unsupported order type"
                );
            }
        }

        ScrapOrders scrapOrder = null;
        BioWasteOrders bioWasteOrder = null;

        
        if (OrderType.SCRAP.equals(orderType)) {

            ScrapRegion scrapRegion =
                    customerAddress.getScrapRegion();

            if (request.customerAddressId() != null && !customerAddressService.isInScrapRegionBoundaries(
                    customerAddress.getLatitude(),
                    customerAddress.getLongitude(),
                    scrapRegion.getId()
            )) {
                throw new InvalidDataException(
                        "Address is outside scrap service area"
                );
            }

            if (!scrapRegionAvailabilityService.bookSlot(
                    scrapRegion.getId(),
                    request.scheduleDate()
            )) {
                throw new InvalidDataException(
                        "Booking slot full for selected date"
                );
            }

            ScrapOrders savedScrapOrder = scrapOrdersRepository.save(
                    new ScrapOrders(
                            null,
                            IdGenerator.nextId(),
                            LocalDateTime.now(),
                            request.scheduleDate(),
                            request.phoneNumber(),
                            null,
                            request.preferredPaymentMethod(),
                            request.comment(),
                            defaultDoubleValue,
                            defaultOrderStatus,
                            "ADMIN",
                            false,
                            customerAddress,
                            null,
                            scrapRegion,
                            customer.getState(),
                            customer,
                            1
                    )
            );
            
            scrapOrder = savedScrapOrder;

            List<ScrapOrderCart> carts =
                    request.typeIds().stream()
                            .map(id ->
                                    scrapTypeRepository
                                            .findByIdAndIsActiveTrue(id)
                                            .orElseThrow(() ->
                                                    new ResourceNotFoundException(
                                                            "Scrap type not found: " + id
                                                    )
                                            )
                            )
                            .map(type ->
                                    new ScrapOrderCart(
                                            null,
                                            0.0,
                                            0.0,
                                            0.0,
                                            false,
                                            savedScrapOrder,
                                            type
                                    )
                            )
                            .toList();
            
            if (carts.isEmpty()) {
                throw new InvalidDataException(
                        "At least one valid scrap type must be selected"
                );
            }


            scrapOrderCartRepository.saveAll(carts);
        }

        
        else if (OrderType.BIO_WASTE.equals(orderType)) {

            Ward ward = customerAddress.getWard();

            if (request.customerAddressId() != null && !customerAddressService.isInLocalBodyBoundaries(
                    customerAddress.getLatitude(),
                    customerAddress.getLongitude(),
                    ward.getLocalbody().getId()
            )) {
                throw new InvalidDataException(
                        "Address outside service area"
                );
            }

            if (!localBodyAvailabilityService.bookSlot(
                    ward.getLocalbody().getId(),
                    request.scheduleDate()
            )) {
                throw new InvalidDataException(
                        "Booking slot full for selected date"
                );
            }

            BioWasteOrders savedBioWasteOrder = bioWasteOrdersRepository.save(
                    new BioWasteOrders(
                            null,
                            IdGenerator.nextId(),
                            LocalDateTime.now(),
                            request.scheduleDate(),
                            defaultDoubleValue,
                            request.phoneNumber(),
                            null,
                            request.preferredPaymentMethod(),
                            request.comment(),
                            defaultOrderStatus,
                            "ADMIN",
                            false,
                            customerAddress,
                            null,
                            state,
                            ward,
                            customer,
                            0,
                            defaultDoubleValue,
                            null,
                            defaultDoubleValue
                    )
            );

            bioWasteOrder = savedBioWasteOrder;
            


            List<BioWasteOrderCart> carts =
                    request.typeIds().stream()
                            .map(id ->
                                    bioWasteTypeRepository
                                            .findById(id)
                                            .orElseThrow(() ->
                                                    new ResourceNotFoundException(
                                                            "Bio-waste type not found: " + id
                                                    )
                                            )
                            )
                            .map(type ->
                                    new BioWasteOrderCart(
                                            null,
                                            false,
                                            savedBioWasteOrder,
                                            type
                                    )
                            )
                            .toList();
            
            if (carts.isEmpty()) {
                throw new InvalidDataException(
                        "At least one valid bio type must be selected"
                );
            }


            bioWasteOrderCartRepository.saveAll(carts);
        }

        
        CompleteOrders completeOrder =
                completeOrdersRepository.save(
                        new CompleteOrders(
                                null,
                                request.scheduleDate(),
                                orderType.getAbbreviation(),
                                defaultOrderStatus,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                defaultDoubleValue,
                                null,
                                null,
                                null,
                                null,
                                null,
                                request.preferredPaymentMethod(),
                                false,
                                null,
                                false,
                                false,
                                bioWasteOrder,
                                null,
                                null,
                                district,
                                null,
                                null,
                                scrapOrder,
                                state,
                                customer,
                                null
                        )
                );

        completeOrderLogRepository.save(
                new CompleteOrderLog(
                        null,
                        "ADMIN",
                        "Order placed by admin",
                        LocalDateTime.now(),
                        null,
                        null,
                        completeOrder,
                        null,
                        customer
                )
        );

        return completeOrder.getId();
    }


}
