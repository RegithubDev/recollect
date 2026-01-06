package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddBwgOrderCartRequest;
import com.resustainability.recollect.dto.request.UpdateBwgOrderCartRequest;
import com.resustainability.recollect.dto.response.IBwgOrderCartResponse;
import com.resustainability.recollect.entity.backend.*;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.BioWasteTypeRepository;
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
    private final BioWasteTypeRepository bioWasteTypeRepository;

    public BwgOrderCartService(
            BwgOrderCartRepository cartRepository,
            BwgOrdersRepository ordersRepository,
            ScrapTypeRepository scrapTypeRepository,
            BioWasteTypeRepository bioWasteTypeRepository
    ) {
        this.cartRepository = cartRepository;
        this.ordersRepository = ordersRepository;
        this.scrapTypeRepository = scrapTypeRepository;
        this.bioWasteTypeRepository = bioWasteTypeRepository;
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

        ScrapType scrapType = null;
        BioWasteType bioWasteType = null;
        if (null != request.scrapTypeId()) {
            scrapType = scrapTypeRepository.findById(request.scrapTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Scrap type not found"));
        } else {
            bioWasteType = bioWasteTypeRepository.findById(request.bioWasteTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Biowaste type not found"));
        }

        BwgOrderCart cart = new BwgOrderCart();
        cart.setBwgOrder(order);
        cart.setScrapType(scrapType);
        cart.setBioWasteType(bioWasteType);
        cart.setScrapWeight(request.scrapWeight());
        cart.setScrapPrice(request.scrapPrice());
        cart.setScrapGst(request.scrapGst());
        cart.setScrapHsn(request.scrapHsn());
        cart.setTotalPrice(
                null == request.scrapWeight() || null == request.scrapPrice()
                        ? null
                        : request.scrapWeight() * request.scrapPrice()
        );
        cart.setIsDeleted(false);

        return cartRepository.save(cart).getId();
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateBwgOrderCartRequest request) {
        ValidationUtils.validateId(request.id());
        ValidationUtils.validateRequestBody(request);

        BwgOrderCart cart = cartRepository.findActiveEntityById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        ScrapType scrapType = null;
        BioWasteType bioWasteType = null;
        if (null != request.scrapTypeId()) {
            scrapType = scrapTypeRepository.findById(request.scrapTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Scrap type not found"));
        } else {
            bioWasteType = bioWasteTypeRepository.findById(request.bioWasteTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Biowaste type not found"));
        }

        cart.setScrapType(scrapType);
        cart.setBioWasteType(bioWasteType);
        cart.setScrapWeight(request.scrapWeight());
        cart.setScrapPrice(request.scrapPrice());
        cart.setScrapGst(request.scrapGst());
        cart.setScrapHsn(request.scrapHsn());
        cart.setTotalPrice(
                null == request.scrapWeight() || null == request.scrapPrice()
                        ? null
                        : request.scrapWeight() * request.scrapPrice()
        );

        cartRepository.save(cart);
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void softDelete(Long id, boolean isDeleted) {

        ValidationUtils.validateId(id);

        if (cartRepository.softDelete(id, isDeleted) == 0) {
            throw new ResourceNotFoundException("Cart item not found");
        }
    }
}
