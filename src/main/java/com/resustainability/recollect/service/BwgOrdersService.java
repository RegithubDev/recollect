package com.resustainability.recollect.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.resustainability.recollect.commons.DateTimeFormatUtils;
import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.IdGenerator;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddBwgOrderRequest;
import com.resustainability.recollect.dto.request.UpdateBwgOrderRequest;
import com.resustainability.recollect.dto.response.IBwgOrderResponse;
import com.resustainability.recollect.entity.backend.BwgClient;
import com.resustainability.recollect.entity.backend.BwgOrders;
import com.resustainability.recollect.entity.backend.CompleteOrderLog;
import com.resustainability.recollect.entity.backend.CompleteOrders;
import com.resustainability.recollect.entity.backend.State;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.BwgClientRepository;
import com.resustainability.recollect.repository.BwgOrdersRepository;
import com.resustainability.recollect.repository.CompleteOrderLogRepository;
import com.resustainability.recollect.repository.CompleteOrdersRepository;
import com.resustainability.recollect.repository.OrderCancelReasonRepository;
import com.resustainability.recollect.repository.StateRepository;
import com.resustainability.recollect.tag.OrderStatus;
import com.resustainability.recollect.tag.OrderType;


@Service
public class BwgOrdersService {

    private final BwgOrdersRepository ordersRepository;
    private final BwgClientRepository clientRepository;
    private final CompleteOrdersRepository completeOrdersRepository;
    private final CompleteOrderLogRepository completeOrderLogRepository;


    @Autowired
    public BwgOrdersService(
            BwgOrdersRepository ordersRepository,
            BwgClientRepository clientRepository,
            StateRepository stateRepository,
            OrderCancelReasonRepository reasonRepository,
            CompleteOrdersRepository completeOrdersRepository,
            CompleteOrderLogRepository completeOrderLogRepository
    ) {
        this.ordersRepository = ordersRepository;
        this.clientRepository = clientRepository;
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
    public IBwgOrderResponse getById(Long id) {
        ValidationUtils.validateId(id);
        return ordersRepository.findOrderById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                		Default.ERROR_NOT_FOUND_ORDER
                		)
                	);
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
                        null
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

    
    

    
//    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//    public Long add(AddBwgOrderRequest request) {
//
//        ValidationUtils.validateRequestBody(request);
//
//       
//        BwgClient client = clientRepository.findById(request.clientId())
//                .orElseThrow(() -> new ResourceNotFoundException(
//                		Default.ERROR_NOT_FOUND_BWG_CLIENT
//                		)
//                	);
//
//      
//        State state = client.getState();
//        if (state == null) {
//            throw new ResourceNotFoundException(
//            		Default.ERROR_NOT_FOUND_BWG_CLIENT_STATE
//            	);
//        }
//
//       
//          BwgOrders order = new BwgOrders();
//        order.setOrderCode(IdGenerator.nextId());           
//        order.setOrderDate(LocalDateTime.now());
//        order.setScheduleDate(request.scheduleDate());
//
//        
//        order.setOrderType("SCRAP");                        
//        order.setOrderStatus("Open");
//        order.setDueSettled(false);
//        order.setDeleted(false);
//        order.setOrderRating(0.0);
//
//        
//        order.setPreferredPaymentMethod(request.preferredPaymentMethod());
//        order.setComment(request.comment());
//
//       
//        order.setClient(client);
//        order.setState(state);
//        
//        BwgOrders entity = ordersRepository.save(order);
//        
//    
//    
//        final CompleteOrders completeOrder = completeOrdersRepository.save(
//                new CompleteOrders(
//                        null,
//                        request.scheduleDate(),
//                        OrderType.BWG.getAbbreviation(),
//                        OrderStatus.OPEN.getAbbreviation(),
//                        null,
//                        null,
//                        null,
//                        null,
//                        null,
//                        null,
//                        null,
//                        null,
//                        0.0,
//                        null,
//                        null,
//                        null,
//                        null,
//                        null,
//                        null,
//                        false,
//                        null,
//                        false,
//                        false,
//                        null,
//                        entity,
//                        client,
//                        client.getDistrict(),
//                        null,
//                        null,
//                        null,
//                        state,
//                        null
//                )
//        );
//        
//        completeOrderLogRepository.save(
//                new CompleteOrderLog(
//                        null,
//                        "Server",
//                        String.format(
//                                "Order Placed with Schedule Date %s by ",
//                                DateTimeFormatUtils.toIsoDate(request.scheduleDate())
//                        ),
//                        LocalDateTime.now(),
//                        null,
//                        client,
//                        completeOrder,
//                        null,
//                        null
//                )
//        );
//
//        return entity.getId();
//    }

    
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateBwgOrderRequest request) {

        ValidationUtils.validateRequestBody(request);

        BwgOrders order = ordersRepository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(
                		Default.ERROR_NOT_FOUND_ORDER
                		)
                	);

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
