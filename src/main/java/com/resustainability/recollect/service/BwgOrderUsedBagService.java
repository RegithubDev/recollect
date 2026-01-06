package com.resustainability.recollect.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

        // Compute
        final BigDecimal quantity = BigDecimal.valueOf(
                request.numberOfBags() == null || request.numberOfBags() < 1
                        ? 1
                        : request.numberOfBags()
        );

        final BigDecimal bagPrice = BigDecimal.valueOf(bag.getBagPrice());
        final BigDecimal totalBagPrice = bagPrice.multiply(quantity);
        final BigDecimal divisor = BigDecimal.valueOf(100);
        final int scale = 2;

        final BigDecimal cgst = totalBagPrice
                .multiply(BigDecimal.valueOf(bag.getBagCgst()))
                .divide(divisor, scale, RoundingMode.HALF_UP);

        final BigDecimal sgst = totalBagPrice
                .multiply(BigDecimal.valueOf(bag.getBagSgst()))
                .divide(divisor, scale, RoundingMode.HALF_UP);

        final BigDecimal finalPrice = totalBagPrice.add(cgst).add(sgst);

        final BwgOrderUsedBag entity = new BwgOrderUsedBag();
        entity.setOrder(order);
        entity.setBag(bag);
        entity.setNumberOfBags(quantity.intValue());

        entity.setTotalBagPrice(
                totalBagPrice.setScale(scale, RoundingMode.HALF_UP).doubleValue()
        );

        entity.setCgstPrice(cgst);
        entity.setSgstPrice(sgst);

        entity.setFinalPrice(
                finalPrice.setScale(scale, RoundingMode.HALF_UP).doubleValue()
        );

        entity.setBagDate(LocalDateTime.now());
        entity.setIsDeleted(false);

        return repository.save(entity).getId();
    }

    
    @Transactional
    public void update(UpdateBwgOrderUsedBagRequest request) {
        ValidationUtils.validateId(request.id());
        ValidationUtils.validateRequestBody(request);

        BwgOrderUsedBag entity = repository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException("Used bag not found"));

        BwgBagPrice bag = bagRepository.findById(request.bagId())
                .orElseThrow(() -> new ResourceNotFoundException("Bag not found"));

        // Compute
        final BigDecimal quantity = BigDecimal.valueOf(
                request.numberOfBags() == null || request.numberOfBags() < 1
                        ? 1
                        : request.numberOfBags()
        );

        final BigDecimal bagPrice = BigDecimal.valueOf(bag.getBagPrice());
        final BigDecimal totalBagPrice = bagPrice.multiply(quantity);
        final BigDecimal divisor = BigDecimal.valueOf(100);
        final int scale = 2;

        final BigDecimal cgst = totalBagPrice
                .multiply(BigDecimal.valueOf(bag.getBagCgst()))
                .divide(divisor, scale, RoundingMode.HALF_UP);

        final BigDecimal sgst = totalBagPrice
                .multiply(BigDecimal.valueOf(bag.getBagSgst()))
                .divide(divisor, scale, RoundingMode.HALF_UP);

        final BigDecimal finalPrice = totalBagPrice.add(cgst).add(sgst);

        entity.setBag(bag);
        entity.setNumberOfBags(quantity.intValue());

        entity.setTotalBagPrice(
                totalBagPrice.setScale(scale, RoundingMode.HALF_UP).doubleValue()
        );

        entity.setCgstPrice(cgst);
        entity.setSgstPrice(sgst);

        entity.setFinalPrice(
                finalPrice.setScale(scale, RoundingMode.HALF_UP).doubleValue()
        );

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
