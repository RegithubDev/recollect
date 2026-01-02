package com.resustainability.recollect.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.request.AddBwgOrderUsedBagRequest;
import com.resustainability.recollect.dto.request.UpdateBwgOrderUsedBagRequest;
import com.resustainability.recollect.dto.response.IBwgOrderUsedBagResponse;
import com.resustainability.recollect.entity.backend.BwgBagPrice;
import com.resustainability.recollect.entity.backend.BwgOrderUsedBag;
import com.resustainability.recollect.entity.backend.CompleteOrders;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.BwgBagPriceRepository;
import com.resustainability.recollect.repository.BwgOrderUsedBagRepository;
import com.resustainability.recollect.repository.CompleteOrdersRepository;

import jakarta.transaction.Transactional;

@Service
public class BwgOrderUsedBagService {

    private final BwgOrderUsedBagRepository repository;
    private final BwgBagPriceRepository bagRepository;
    private final CompleteOrdersRepository orderRepository;

    public BwgOrderUsedBagService(
            BwgOrderUsedBagRepository repository,
            BwgBagPriceRepository bagRepository,
            CompleteOrdersRepository orderRepository
    ) {
        this.repository = repository;
        this.bagRepository = bagRepository;
        this.orderRepository = orderRepository;
    }

    
    @Transactional
    public Long add(AddBwgOrderUsedBagRequest request) {

        ValidationUtils.validateRequestBody(request);

        CompleteOrders order = orderRepository.findById(request.orderId())
               .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        BwgBagPrice bag = bagRepository.findById(request.bagId())
                .orElseThrow(() -> new ResourceNotFoundException("Bag not found"));

        int qty = request.numberOfBags();

        double totalBagPrice = qty * bag.getBagPrice();

        BigDecimal cgst = BigDecimal.valueOf(totalBagPrice)
                .multiply(BigDecimal.valueOf(bag.getBagCgst()))
                .divide(BigDecimal.valueOf(100));

        BigDecimal sgst = BigDecimal.valueOf(totalBagPrice)
                .multiply(BigDecimal.valueOf(bag.getBagSgst()))
                .divide(BigDecimal.valueOf(100));

        double finalPrice = totalBagPrice
                + cgst.doubleValue()
                + sgst.doubleValue();

        BwgOrderUsedBag entity = new BwgOrderUsedBag();
        entity.setOrder(order);
        entity.setBag(bag);
        entity.setNumberOfBags(qty);
        entity.setTotalBagPrice(totalBagPrice);
        entity.setCgstPrice(cgst);
        entity.setSgstPrice(sgst);
        entity.setFinalPrice(finalPrice);
        entity.setBagDate(LocalDateTime.now());
        entity.setIsDeleted(false);

        return repository.save(entity).getId();
    }

    
    @Transactional
    public void update(UpdateBwgOrderUsedBagRequest request) {

        ValidationUtils.validateRequestBody(request);

        BwgOrderUsedBag entity = repository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException("Used bag not found"));

        BwgBagPrice bag = bagRepository.findById(request.bagId())
                .orElseThrow(() -> new ResourceNotFoundException("Bag not found"));

        int qty = request.numberOfBags();

        double totalBagPrice = qty * bag.getBagPrice();

        BigDecimal cgst = BigDecimal.valueOf(totalBagPrice)
                .multiply(BigDecimal.valueOf(bag.getBagCgst()))
                .divide(BigDecimal.valueOf(100));

        BigDecimal sgst = BigDecimal.valueOf(totalBagPrice)
                .multiply(BigDecimal.valueOf(bag.getBagSgst()))
                .divide(BigDecimal.valueOf(100));

        double finalPrice = totalBagPrice
                + cgst.doubleValue()
                + sgst.doubleValue();

        entity.setBag(bag);
        entity.setNumberOfBags(qty);
        entity.setTotalBagPrice(totalBagPrice);
        entity.setCgstPrice(cgst);
        entity.setSgstPrice(sgst);
        entity.setFinalPrice(finalPrice);

        repository.save(entity);
    }

    
    public Page<IBwgOrderUsedBagResponse> listByOrder(Long orderId, Pageable pageable) {
        return repository.findAllByOrderId(orderId, pageable);
    }

    
    @Transactional
    public void delete(Long id) {
        if (repository.softDelete(id, true) == 0) {
            throw new ResourceNotFoundException("Used bag not found");
        }
    }

   
    @Transactional
    public void undelete(Long id) {
        if (repository.softDelete(id, false) == 0) {
            throw new ResourceNotFoundException("Used bag not found");
        }
    }
}
