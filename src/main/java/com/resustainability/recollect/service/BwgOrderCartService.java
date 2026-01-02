package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddBwgOrderCartRequest;
import com.resustainability.recollect.dto.request.UpdateBwgOrderCartRequest;
import com.resustainability.recollect.dto.response.IBwgOrderCartResponse;
import com.resustainability.recollect.entity.backend.*;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.BwgOrderCartRepository;
import com.resustainability.recollect.repository.BwgOrdersRepository;
import com.resustainability.recollect.repository.ScrapTypeRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BwgOrderCartService {

    private final BwgOrderCartRepository cartRepository;
    private final BwgOrdersRepository ordersRepository;
    private final ScrapTypeRepository scrapTypeRepository;

    public BwgOrderCartService(
            BwgOrderCartRepository cartRepository,
            BwgOrdersRepository ordersRepository,
            ScrapTypeRepository scrapTypeRepository
    ) {
        this.cartRepository = cartRepository;
        this.ordersRepository = ordersRepository;
        this.scrapTypeRepository = scrapTypeRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<IBwgOrderCartResponse> list(Long orderId, SearchCriteria searchCriteria) {

        ValidationUtils.validateOrderId(orderId);

        return Pager.of(
                cartRepository.findAllResponsesByOrderId(
                        orderId,
                        searchCriteria.toPageRequest()
                )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)  
    public IBwgOrderCartResponse getById(Long id) {

        ValidationUtils.validateId(id);

        return cartRepository.findResponseById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
    }

  
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddBwgOrderCartRequest request) {

        ValidationUtils.validateRequestBody(request);

        BwgOrders order = ordersRepository.findById(request.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        ScrapType scrapType = scrapTypeRepository.findById(request.scrapTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Scrap type not found"));

        BwgOrderCart cart = new BwgOrderCart();
        cart.setBwgOrder(order);
        cart.setScrapType(scrapType);
        cart.setScrapWeight(request.scrapWeight());
        cart.setScrapPrice(request.scrapPrice());
        cart.setScrapGst(0.0);
        cart.setScrapHsn(null);
        cart.setTotalPrice(request.scrapWeight() * request.scrapPrice());
        cart.setIsDeleted(false);

        return cartRepository.save(cart).getId();
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateBwgOrderCartRequest request) {

        ValidationUtils.validateRequestBody(request);

        BwgOrderCart cart = cartRepository.findActiveEntityById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        cart.setScrapWeight(request.scrapWeight());
        cart.setScrapPrice(request.scrapPrice());
        cart.setScrapGst(request.scrapGst());
        cart.setScrapHsn(request.scrapHsn());
        cart.setTotalPrice(request.scrapWeight() * request.scrapPrice());
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void softDelete(Long id, boolean isDeleted) {

        ValidationUtils.validateId(id);

        if (cartRepository.softDelete(id, isDeleted) == 0) {
            throw new ResourceNotFoundException("Cart item not found");
        }
    }
}
