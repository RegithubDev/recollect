package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.CancelOrderRequest;
import com.resustainability.recollect.dto.request.PlaceOrderRequest;
import com.resustainability.recollect.dto.response.IOrderHistoryResponse;
import com.resustainability.recollect.dto.response.IOrderCancelReasonResponse;
import com.resustainability.recollect.dto.response.IUserContext;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.exception.UnauthorizedException;
import com.resustainability.recollect.repository.*;

import com.resustainability.recollect.tag.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    private final SecurityService securityService;
    private final OrderCancelReasonRepository orderCancelReasonRepository;
    private final CompleteOrdersRepository completeOrdersRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public OrderService(
            SecurityService securityService,
            OrderCancelReasonRepository orderCancelReasonRepository,
            CompleteOrdersRepository completeOrdersRepository,
            CustomerRepository customerRepository
    ) {
        this.securityService = securityService;
        this.orderCancelReasonRepository = orderCancelReasonRepository;
        this.completeOrdersRepository = completeOrdersRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
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
    public Long placeOrder(PlaceOrderRequest request) {
        ValidationUtils.validateRequestBody(request);

        /*
        if (!customerRepository.existsById(request.customerId())) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_USER);
        }

        if (null != request.scrapRegionId() && !scrapRegionRepository.existsById(request.scrapRegionId())) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_SCRAP_REGION);
        }

        if (null != request.wardId() && !wardRepository.existsById(request.wardId())) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WARD);
        }

        return completeOrdersRepository.save(
                new CustomerAddress(
                        null,
                        Boolean.TRUE.equals(request.isScrapService()),
                        Boolean.TRUE.equals(request.isScrapLocationActive()),
                        Boolean.TRUE.equals(request.isBioWasteService()),
                        Boolean.TRUE.equals(request.isBioWasteLocationActive()),
                        request.residenceType(),
                        request.residenceDetails(),
                        request.landmark(),
                        request.latitude(),
                        request.longitude(),
                        false,
                        null != request.scrapRegionId() ? scrapRegionRepository.getReferenceById(request.scrapRegionId()) : null,
                        null != request.wardId() ? wardRepository.getReferenceById(request.wardId()) : null,
                        customerRepository.getReferenceById(request.customerId())
                )
        ).getId();

         */
        return 0L;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void cancelById(CancelOrderRequest request) {
        ValidationUtils.validateRequestBody(request);
        if (0 == completeOrdersRepository.cancelByCompleteOrderId(
                request.id(),
                true,
                OrderStatus.CANCELLED.getAbbreviation()

        )) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER);
        }

        // TODO: reason_id based on type: table
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void deleteById(Long completeOrderId, boolean value) {
        ValidationUtils.validateId(completeOrderId);
        if (0 == completeOrdersRepository.deleteByCompleteOrderId(
                completeOrderId,
                value
        )) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ORDER);
        }
    }
}
