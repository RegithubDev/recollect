package com.resustainability.recollect.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.request.AddBwgOrderUsedBagRequest;
import com.resustainability.recollect.dto.request.UpdateBwgOrderUsedBagRequest;
import com.resustainability.recollect.dto.response.IBwgOrderUsedBagResponse;
import com.resustainability.recollect.service.BwgOrderUsedBagService;

@RestController
@RequestMapping("/api/v1/bwg-used-bag")
public class BwgOrderUsedBagController {

    private final BwgOrderUsedBagService service;

    public BwgOrderUsedBagController(BwgOrderUsedBagService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public APIResponse<Long> add(@RequestBody AddBwgOrderUsedBagRequest request) {
        return new APIResponse<>("Added Successfully", service.add(request));
    }

    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody UpdateBwgOrderUsedBagRequest request) {
        service.update(request);
        return new APIResponse<>("Updated Successfully");
    }

    @GetMapping("/list/{orderId}")
    public APIResponse<Page<IBwgOrderUsedBagResponse>> list(
            @PathVariable Long orderId,
            Pageable pageable
    ) {
        return new APIResponse<>(service.listByOrder(orderId, pageable));
    }

    @DeleteMapping("/delete/{id}")
    public APIResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new APIResponse<>("Deleted Successfully");
    }

    @DeleteMapping("/undelete/{id}")
    public APIResponse<Void> undelete(@PathVariable Long id) {
        service.undelete(id);
        return new APIResponse<>("Restored Successfully");
    }
}
