package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.*;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.CancelOrderRequest;
import com.resustainability.recollect.dto.request.PlaceOrderRequest;
import com.resustainability.recollect.dto.response.*;
import com.resustainability.recollect.entity.backend.*;
import com.resustainability.recollect.exception.InvalidDataException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.exception.UnauthorizedException;
import com.resustainability.recollect.repository.*;
import com.resustainability.recollect.tag.OrderStatus;
import com.resustainability.recollect.tag.OrderType;
import com.resustainability.recollect.tag.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService {
    private final SecurityService securityService;
    private final ScrapRegionAvailabilityService scrapRegionAvailabilityService;
    private final CustomerAddressRepository customerAddressRepository;
    private final OrderCancelReasonRepository orderCancelReasonRepository;
    private final CompleteOrdersRepository completeOrdersRepository;
    private final CompleteOrderLogRepository completeOrderLogRepository;
    private final ScrapOrdersRepository scrapOrdersRepository;
    private final BioWasteOrdersRepository bioWasteOrdersRepository;
    private final ScrapRegionRepository scrapRegionRepository;
    private final WardRepository wardRepository;
    private final DistrictRepository districtRepository;
    private final StateRepository stateRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public OrderService(
            SecurityService securityService,
            ScrapRegionAvailabilityService scrapRegionAvailabilityService,
            CustomerAddressRepository customerAddressRepository,
            OrderCancelReasonRepository orderCancelReasonRepository,
            CompleteOrdersRepository completeOrdersRepository,
            CompleteOrderLogRepository completeOrderLogRepository,
            ScrapOrdersRepository scrapOrdersRepository,
            BioWasteOrdersRepository bioWasteOrdersRepository,
            ScrapRegionRepository scrapRegionRepository,
            WardRepository wardRepository,
            DistrictRepository districtRepository,
            StateRepository stateRepository,
            CustomerRepository customerRepository
    ) {
        this.securityService = securityService;
        this.scrapRegionAvailabilityService = scrapRegionAvailabilityService;
        this.customerAddressRepository = customerAddressRepository;
        this.orderCancelReasonRepository = orderCancelReasonRepository;
        this.completeOrdersRepository = completeOrdersRepository;
        this.completeOrderLogRepository = completeOrderLogRepository;
        this.scrapOrdersRepository = scrapOrdersRepository;
        this.bioWasteOrdersRepository = bioWasteOrdersRepository;
        this.scrapRegionRepository = scrapRegionRepository;
        this.wardRepository = wardRepository;
        this.districtRepository = districtRepository;
        this.stateRepository = stateRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<IOrderCancelReasonResponse> listCancellationReasons() {
        return orderCancelReasonRepository.findAllOrderCancelReasons();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<IOrderHistoryResponse> listHistory(SearchCriteria searchCriteria) {
        final IUserContext user = securityService
                .getCurrentUser()
                .orElseThrow(UnauthorizedException::new);

        final Pageable pageable = searchCriteria.toPageRequest();

        if (Boolean.TRUE.equals(user.getIsAdmin())) {
            return Pager.of(
                    completeOrdersRepository.findAllPaged(
                            searchCriteria.getQ(),
                            pageable
                    )
            );
        } else if (Boolean.TRUE.equals(user.getIsCustomer())) {
            return Pager.of(
                    completeOrdersRepository.findAllPagedIfBelongs(
                            user.getId(),
                            searchCriteria.getQ(),
                            pageable
                    )
            );
        }

        return Pager.empty(pageable);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IOrderHistoryResponse getById(Long completeOrderId) {
        ValidationUtils.validateId(completeOrderId);

        final IUserContext user = securityService
                .getCurrentUser()
                .orElseThrow(UnauthorizedException::new);

        if (!Boolean.TRUE.equals(user.getIsAdmin())) {
            return completeOrdersRepository
                    .findByCompleteOrderIdIfBelongs(user.getId(), completeOrderId)
                    .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER));
        }

        return completeOrdersRepository
                .findByCompleteOrderId(completeOrderId)
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
        final Ward ward = null != address.getWardId()
                ? wardRepository.getReferenceById(address.getWardId())
                : null;

        final double defaultDoubleValue = Double.NaN;
        final String defaultOrderStatus = OrderStatus.OPEN.getAbbreviation();

        ScrapOrders scrapOrder = null;
        BioWasteOrders bioWasteOrder = null;
        if (OrderType.SCRAP.equals(orderType)) {
            if (null == address.getScrapRegionId()) {
                throw new InvalidDataException("Scrap region is required, Set region in your address.");
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
        } else if (OrderType.BIO_WASTE.equals(orderType)) {
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
                        customer
                )
        );

        final String userRole = Role.fromUserContext(user);

        completeOrderLogRepository.save(
                new CompleteOrderLog(
                        null,
                        String.format(
                                "%s%s%s",
                                user.getFullName(),
                                StringUtils.isNotBlank(user.getPhoneNumber())
                                        ? String.format("/%s", user.getPhoneNumber())
                                        : Default.EMPTY,
                                StringUtils.isNotBlank(userRole)
                                        ? String.format(" - (%s)", userRole)
                                        : Default.EMPTY
                        ),
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

        if (OrderType.is(order.getType(), OrderType.BIO_WASTE) && 0 == bioWasteOrdersRepository.cancelByBioWasteOrderId(
                order.getBioWasteOrderId(),
                request.reasonId(),
                orderStatus
        )) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER);
        }

        if (0 == completeOrdersRepository.cancelByCompleteOrderId(
                request.id(),
                true,
                orderStatus

        )) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER);
        }
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
}
