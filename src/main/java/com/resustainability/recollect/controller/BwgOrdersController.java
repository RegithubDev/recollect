package com.resustainability.recollect.controller;

import com.resustainability.recollect.dto.response.BwgOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddBwgOrderRequest;
import com.resustainability.recollect.dto.request.UpdateBwgOrderRequest;
import com.resustainability.recollect.dto.response.IBwgOrderResponse;
import com.resustainability.recollect.service.BwgOrdersService;
import com.resustainability.recollect.tag.OrderType;

@RestController
@RequestMapping("/api/v1/bwg-orders")
@PreAuthorize("hasRole('ADMIN')")
public class BwgOrdersController {

    private final BwgOrdersService ordersService;

    @Autowired
    public BwgOrdersController(BwgOrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping("/scrap-list")
    public APIResponse<Pager<IBwgOrderResponse>> listScrap(
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
        		ordersService.list(OrderType.SCRAP, searchCriteria)
        	);
    }
    
    @GetMapping("/biowaste-list")
    public APIResponse<Pager<IBwgOrderResponse>> listBioWaste(
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
        		ordersService.list(OrderType.BIO_WASTE, searchCriteria)
        	);
    }


    @GetMapping("/details/{id}")
    public APIResponse<BwgOrderResponse> get(@PathVariable Long id) {
        return new APIResponse<>(
                ordersService.getById(id)
        );
    }

    
    @PostMapping("/scrap-add")
    public APIResponse<Long> addScrap(@RequestBody AddBwgOrderRequest request) {
        return new APIResponse<>(
                Default.SUCCESS_ORDER_PLACED,
                ordersService.add(request, OrderType.SCRAP)
        );
    }

    
    @PostMapping("/biowaste-add")
    public APIResponse<Long> addBioWaste(@RequestBody AddBwgOrderRequest request) {
        return new APIResponse<>(
                Default.SUCCESS_ORDER_PLACED,
                ordersService.add(request, OrderType.BIO_WASTE)
        );
    }


    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody UpdateBwgOrderRequest request) {
        ordersService.update(request);
        return new APIResponse<>(
        		Default.SUCCESS_UPDATE_ORDER_DETAILS
        	);
    }
 
    
    @DeleteMapping("/delete/{orderId}")
    public APIResponse<Void> delete(@PathVariable Long orderId) {
        ordersService.softDelete(orderId, true);
        return new APIResponse<>(Default.SUCCESS_DELETE_ORDER);
    }

    
    @DeleteMapping("/un-delete/{orderId}")
    public APIResponse<Void> undelete(@PathVariable Long orderId) {
        ordersService.softDelete(orderId, false);
        return new APIResponse<>(Default.SUCCESS_UNDELETE_ORDER);
    }

}
