package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddBwgBagPriceRequest;
import com.resustainability.recollect.dto.request.UpdateBwgBagPriceRequest;
import com.resustainability.recollect.dto.response.IBwgBagPriceResponse;
import com.resustainability.recollect.service.BwgBagPriceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bwg-bag-price")
public class BwgBagPriceController {

    private final BwgBagPriceService service;

    public BwgBagPriceController(BwgBagPriceService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public APIResponse<Pager<IBwgBagPriceResponse>> list(
            @RequestParam(required = false) Long clientId,
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                service.list(clientId, searchCriteria)
        );
    }

    @GetMapping("/details/{id}")
    public APIResponse<IBwgBagPriceResponse> getById(@PathVariable Long id) {
        return new APIResponse<>(
                service.getById(id)
        );
    }

    @PostMapping("/add")
    public APIResponse<Long> add(@RequestBody AddBwgBagPriceRequest request) {
        return new APIResponse<>(
                Default.SUCCESS_ADD_BWG_BAG_PRICE,
                service.add(request)
        );
    }

    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody UpdateBwgBagPriceRequest request) {
        service.update(request);
        return new APIResponse<>(
                Default.SUCCESS_UPDATE_BWG_BAG_PRICE
        );
    }
}
