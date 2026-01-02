package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddBwgOrderCartRequest;
import com.resustainability.recollect.dto.request.UpdateBwgOrderCartRequest;
import com.resustainability.recollect.dto.response.IBwgOrderCartResponse;
import com.resustainability.recollect.service.BwgOrderCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bwg-order-cart")
@PreAuthorize("hasRole('ADMIN')")
public class BwgOrderCartController {

    private final BwgOrderCartService cartService;

    @Autowired
    public BwgOrderCartController(BwgOrderCartService cartService) {
        this.cartService = cartService;
    }

  
    @GetMapping("/list")
    public APIResponse<Pager<IBwgOrderCartResponse>> list(
            @RequestParam Long orderId,
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                cartService.list(orderId, searchCriteria)
        );
    }

    @GetMapping("/details/{id}")
    public APIResponse<IBwgOrderCartResponse> getById(
            @PathVariable Long id
    ) {
        return new APIResponse<>(
                cartService.getById(id)
        );
    }


    @PostMapping("/add")
    public APIResponse<Long> add(
            @RequestBody AddBwgOrderCartRequest request
    ) {
        return new APIResponse<>(
                Default.SUCCESS,
                cartService.add(request)
        );
    }

    @PutMapping("/update")
    public APIResponse<Void> update(
            @RequestBody UpdateBwgOrderCartRequest request
    ) {
        cartService.update(request);
        return new APIResponse<>(Default.SUCCESS);
    }


    @DeleteMapping("/delete/{id}")
    public APIResponse<Void> delete(@PathVariable Long id) {
        cartService.softDelete(id, true);
        return new APIResponse<>(Default.SUCCESS);
    }

    @DeleteMapping("/un-delete/{id}")
    public APIResponse<Void> undelete(@PathVariable Long id) {
        cartService.softDelete(id, false);
        return new APIResponse<>(Default.SUCCESS);
    }
}
