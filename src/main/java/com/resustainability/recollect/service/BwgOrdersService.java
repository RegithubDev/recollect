package com.resustainability.recollect.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.resustainability.recollect.commons.*;
import com.resustainability.recollect.dto.request.*;
import com.resustainability.recollect.dto.response.*;
import com.resustainability.recollect.entity.backend.*;
import com.resustainability.recollect.repository.*;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.tag.OrderStatus;
import com.resustainability.recollect.tag.OrderType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BwgOrdersService {
    private final BwgOrdersRepository ordersRepository;
    private final BwgOrderCartRepository bwgOrderCartRepository;
    private final BioWasteTypeRepository bioWasteTypeRepository;
    private final ScrapTypeRepository scrapTypeRepository;
    private final BwgOrderUsedBagRepository bwgOrderUsedBagRepository;
    private final BwgClientRepository clientRepository;
    private final CompleteOrdersRepository completeOrdersRepository;
    private final CompleteOrderLogRepository completeOrderLogRepository;

    @Autowired
    public BwgOrdersService(
            BwgOrdersRepository ordersRepository,
            BioWasteTypeRepository bioWasteTypeRepository,
            BwgClientRepository clientRepository,
            BwgOrderCartRepository bwgOrderCartRepository,
            ScrapTypeRepository scrapTypeRepository,
            BwgOrderUsedBagRepository bwgOrderUsedBagRepository,
            CompleteOrdersRepository completeOrdersRepository,
            CompleteOrderLogRepository completeOrderLogRepository
    ) {
        this.ordersRepository = ordersRepository;
        this.bioWasteTypeRepository = bioWasteTypeRepository;
        this.clientRepository = clientRepository;
        this.bwgOrderCartRepository = bwgOrderCartRepository;
        this.scrapTypeRepository = scrapTypeRepository;
        this.bwgOrderUsedBagRepository = bwgOrderUsedBagRepository;
        this.completeOrdersRepository = completeOrdersRepository;
        this.completeOrderLogRepository = completeOrderLogRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<IBwgOrderResponse> list(OrderType orderType, SearchCriteria searchCriteria) {
        return Pager.of(
                ordersRepository.findAllPaged(
                		orderType.getAbbreviation(),
                		searchCriteria.toPageRequest())
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public BwgOrderResponse getById(Long id) {
        ValidationUtils.validateId(id);

        final IBwgOrderResponse details = ordersRepository
                .findOrderById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER));

        final List<ItemCategoryTypeResponse> types = bwgOrderCartRepository
                .findAllTypesByOrderId(id);

        final List<IBwgOrderUsedBagResponse> usedBags = bwgOrderUsedBagRepository
                .findAllUsedBagsByOrderId(id);

        final InvoiceResponse invoice = completeOrdersRepository
                .findInvoiceDetailsByBwgOrderId(id)
                .orElseGet(() -> new InvoiceResponse(
                        id,
                        details.getOrderType(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                ));

        return new BwgOrderResponse(details, types, usedBags, invoice);
    }

    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED
    )
    public Long add(AddBwgOrderRequest request, OrderType orderType) {

        
        ValidationUtils.validateRequestBody(request);

        
        if (orderType == null) {
            throw new IllegalArgumentException("Order type must not be null or empty");
        }

        
        BwgClient client = clientRepository.findById(request.clientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        Default.ERROR_NOT_FOUND_BWG_CLIENT
                ));

       
        State state = client.getState();
        if (state == null) {
            throw new ResourceNotFoundException(
                    Default.ERROR_NOT_FOUND_BWG_CLIENT_STATE
            );
        }

        
        BwgOrders order = new BwgOrders();
        order.setOrderCode(IdGenerator.nextId());
        order.setOrderDate(LocalDateTime.now());
        order.setScheduleDate(request.scheduleDate());

        
        order.setOrderType(orderType.getAbbreviation());

        order.setOrderStatus(OrderStatus.OPEN.getAbbreviation());
        order.setDueSettled(false);
        order.setDeleted(false);
        order.setOrderRating(0.0);

        order.setPreferredPaymentMethod(request.preferredPaymentMethod());
        order.setComment(request.comment());

        
        order.setClient(client);
        order.setState(state);

        
        BwgOrders entity = ordersRepository.save(order);

        final CompleteOrders completeOrder = completeOrdersRepository.save(
                new CompleteOrders(
                        null,
                        request.scheduleDate(),
                        OrderType.BWG.getAbbreviation(),
                        OrderStatus.OPEN.getAbbreviation(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        0.0,
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
                        null,
                        entity,
                        client,
                        client.getDistrict(),
                        null,
                        null,
                        null,
                        state,
                        null,
                        client.getClientPrice()
                )
        );
        
        completeOrderLogRepository.save(
                new CompleteOrderLog(
                        null,
                        "Server",
                        String.format(
                                "Order Placed with Schedule Date %s by ",
                                DateTimeFormatUtils.toIsoDate(request.scheduleDate())
                        ),
                        LocalDateTime.now(),
                        null,
                        client,
                        completeOrder,
                        null,
                        null
                )
        );

        return entity.getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateBwgOrderRequest request) {
        ValidationUtils.validateRequestBody(request);

        BwgOrders order = ordersRepository
                .findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER));

        if (request.scheduleDate() != null) {
            order.setScheduleDate(request.scheduleDate());
        }

        if (request.comment() != null) {
            order.setComment(request.comment());
        }

        if (request.preferredPaymentMethod() != null) {
            order.setPreferredPaymentMethod(request.preferredPaymentMethod());
        }

        if (request.orderStatus() != null) {
            order.setOrderStatus(request.orderStatus());
        }

        ordersRepository.save(order);

        // Types
        final Map<Long, BwgOrderCart> existingTypesEntity = bwgOrderCartRepository
                .findAllWhereOrderIdIs(request.id())
                .stream()
                .collect(Collectors.toMap(
                        BwgOrderCart::getId,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));

        final Map<Long, BioWasteType> indexedBioWasteType = CollectionUtils.isBlank(request.types()) ? Collections.emptyMap() : bioWasteTypeRepository
                .findAllById(request
                        .types()
                        .stream()
                        .filter(Objects::nonNull)
                        .map(UpdateBwgOrderCartRequest::bioWasteTypeId)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet())
                )
                .stream()
                .collect(Collectors.toMap(
                        BioWasteType::getId,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));

        final Map<Long, ScrapType> indexedScrapType = CollectionUtils.isBlank(request.types()) ? Collections.emptyMap() : scrapTypeRepository
                .findAllById(request
                        .types()
                        .stream()
                        .filter(Objects::nonNull)
                        .map(UpdateBwgOrderCartRequest::scrapTypeId)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet())
                )
                .stream()
                .collect(Collectors.toMap(
                        ScrapType::getId,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));

        final List<BwgOrderCart> typesEntityToSave = CollectionUtils.isBlank(request.types()) ? Collections.emptyList() : request
                .types()
                .stream()
                .filter(Objects::nonNull)
                .map(cartRequest -> {
                    final BioWasteType bioWasteType = indexedBioWasteType.getOrDefault(cartRequest.bioWasteTypeId(), null);
                    final ScrapType scrapType = indexedScrapType.getOrDefault(cartRequest.scrapTypeId(), null);
                    final Double totalPrice = null == cartRequest.scrapWeight() || null == cartRequest.scrapPrice()
                            ? null
                            : cartRequest.scrapWeight() * cartRequest.scrapPrice();

                    if (null == cartRequest.id() || !existingTypesEntity.containsKey(cartRequest.id())) {
                        return new BwgOrderCart(
                                null,
                                cartRequest.scrapWeight(),
                                cartRequest.scrapPrice(),
                                totalPrice,
                                false,
                                bioWasteType,
                                order,
                                scrapType,
                                cartRequest.scrapGst(),
                                cartRequest.scrapHsn()
                        );
                    }

                    final BwgOrderCart orderCart = existingTypesEntity.remove(cartRequest.id());
                    orderCart.setScrapType(scrapType);
                    orderCart.setBioWasteType(bioWasteType);
                    orderCart.setScrapWeight(cartRequest.scrapWeight());
                    orderCart.setScrapPrice(cartRequest.scrapPrice());
                    orderCart.setScrapGst(cartRequest.scrapGst());
                    orderCart.setScrapHsn(cartRequest.scrapHsn());
                    orderCart.setTotalPrice(totalPrice);
                    return orderCart;
                })
                .toList();

        if (CollectionUtils.isNonBlank(typesEntityToSave)) {
            bwgOrderCartRepository.saveAll(typesEntityToSave);
        }

        if (!existingTypesEntity.isEmpty()) {
            bwgOrderCartRepository.deleteAllInBatch(existingTypesEntity.values());
        }

        // Used bags
        final Map<Long, BwgOrderUsedBag> existingUsedBagsEntity = bwgOrderUsedBagRepository
                .findAllWhereOrderIdIs(request.id())
                .stream()
                .collect(Collectors.toMap(
                        BwgOrderUsedBag::getId,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));

        final BigDecimal divisor = BigDecimal.valueOf(100);
        final int scale = 2;

        /*
        final List<BwgOrderUsedBag> usedBagsEntityToSave = CollectionUtils.isBlank(request.usedBags()) ? Collections.emptyList() : request
                .usedBags()
                .stream()
                .filter(Objects::nonNull)
                .map(usedBagRequest -> {
                    // Compute
                    final BigDecimal quantity = BigDecimal.valueOf(
                            usedBagRequest.numberOfBags() == null || usedBagRequest.numberOfBags() < 1
                                    ? 1
                                    : usedBagRequest.numberOfBags()
                    );

                    final BigDecimal bagPrice = BigDecimal.valueOf(bag.getBagPrice());
                    final BigDecimal totalBagPrice = bagPrice.multiply(quantity);

                    final BigDecimal cgst = totalBagPrice
                            .multiply(BigDecimal.valueOf(bag.getBagCgst()))
                            .divide(divisor, scale, RoundingMode.HALF_UP);

                    final BigDecimal sgst = totalBagPrice
                            .multiply(BigDecimal.valueOf(bag.getBagSgst()))
                            .divide(divisor, scale, RoundingMode.HALF_UP);

                    final BigDecimal finalPrice = totalBagPrice.add(cgst).add(sgst);

                    if (null == usedBagRequest.id() || !existingUsedBagsEntity.containsKey(usedBagRequest.id())) {
                        return new BwgOrderUsedBag(
                                null,
                                quantity.intValue(),
                                totalBagPrice.setScale(scale, RoundingMode.HALF_UP).doubleValue(),
                                cgst,
                                sgst,
                                finalPrice.setScale(scale, RoundingMode.HALF_UP).doubleValue(),
                                LocalDateTime.now(),
                                false,
                                bag,
                                completeOrder
                        );
                    }

                    final BwgOrderUsedBag usedBag = existingUsedBagsEntity.remove(usedBagRequest.id());
                    usedBag.setOrder(completeOrder);
                    usedBag.setBag(bag);
                    usedBag.setNumberOfBags(quantity.intValue());

                    usedBag.setTotalBagPrice(
                            totalBagPrice.setScale(scale, RoundingMode.HALF_UP).doubleValue()
                    );

                    usedBag.setCgstPrice(cgst);
                    usedBag.setSgstPrice(sgst);

                    usedBag.setFinalPrice(
                            finalPrice.setScale(scale, RoundingMode.HALF_UP).doubleValue()
                    );
                    return usedBag;
                })
                .toList();

        if (CollectionUtils.isNonBlank(usedBagsEntityToSave)) {
            bwgOrderUsedBagRepository.saveAll(usedBagsEntityToSave);
        }

        if (!existingUsedBagsEntity.isEmpty()) {
            bwgOrderUsedBagRepository.deleteAllInBatch(existingUsedBagsEntity.values());
        }

        // TODO - INVOICE COMPUTE
         */
    }

  /*  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void updateScheduledDate(UpdateBwgOrderScheduleDateRequest request) {
        ValidationUtils.validateRequestBody(request);
        if (ordersRepository.updateScheduledDate(request.id(), request.scheduleDate()) == 0) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER);
        }
    }*/
    
  /*  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void updateScheduledDate(UpdateBwgOrderScheduleDateRequest request) {

        ValidationUtils.validateRequestBody(request);

        int updatedOrders = ordersRepository
                .updateScheduledDate(request.id(), request.scheduleDate());

        if (updatedOrders == 0) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER);
        }

        completeOrdersRepository
                .updateScheduledDateByBwgOrderId(
                        request.id(),
                        request.scheduleDate()
                );
    }*/
    
    
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void updateScheduledDate(UpdateBwgOrderScheduleDateRequest request) {

        ValidationUtils.validateRequestBody(request);

      
        int updated = ordersRepository.updateScheduledDate(
                request.id(),
                request.scheduleDate()
        );

        if (updated == 0) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER);
        }

     
        CompleteOrders completeOrder =
                completeOrdersRepository.findByBwgOrder_Id(request.id())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Complete order not found"));

        completeOrdersRepository.updateScheduledDateByBwgOrderId(
                request.id(),
                request.scheduleDate()
        );

 
        CompleteOrderLog log = new CompleteOrderLog();
        log.setOrder(completeOrder);
        log.setDoneBy("Server");
        log.setCreatedAt(LocalDateTime.now());
        log.setClient(completeOrder.getClient());

        String description =
                "Order Schedule Date Updated to " + request.scheduleDate()
                + " and Order Status Updated to "
                + completeOrder.getOrderStatus()
                + " by";

        log.setDescription(description);

        completeOrderLogRepository.save(log);
    }



    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void softDelete(Long id, boolean isDeleted) {

        int updated = ordersRepository.softDelete(id, isDeleted);

        if (updated == 0) {
            throw new ResourceNotFoundException(
                    Default.ERROR_NOT_FOUND_ORDER
            );
        }
    }

}
