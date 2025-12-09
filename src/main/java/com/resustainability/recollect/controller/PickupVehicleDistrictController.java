package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddPickupVehicleDistrictRequest;
import com.resustainability.recollect.dto.request.UpdatePickupVehicleDistrictRequest;
import com.resustainability.recollect.dto.response.IPickupVehicleDistrictResponse;
import com.resustainability.recollect.service.PickupVehicleDistrictService;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/recollect/v1/pickupvehicledistrict")
@PreAuthorize("hasRole('ADMIN')")
public class PickupVehicleDistrictController {

    private final PickupVehicleDistrictService service;

    public PickupVehicleDistrictController(PickupVehicleDistrictService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public APIResponse<Pager<IPickupVehicleDistrictResponse>> list(
            @RequestParam(required = false) Long districtId,
            @RequestParam(required = false) Long vehicleId,
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                service.list(districtId, vehicleId, searchCriteria)
        );
    }

    @GetMapping("/details/{id}")
    public APIResponse<IPickupVehicleDistrictResponse> getById(@PathVariable Long id) {
        return new APIResponse<>(service.getById(id));
    }

    @PostMapping("/add")
    public APIResponse<Long> add(@RequestBody AddPickupVehicleDistrictRequest request) {
        return new APIResponse<>(
                Default.SUCCESS_ADD_PICKUPVEHICLEDISTRICT,
                service.add(request)
        );
    }

    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody UpdatePickupVehicleDistrictRequest request) {
        service.update(request);
        return new APIResponse<>(
        		Default.SUCCESS_UPDATE_PICKUPVEHICLEDISTRICT
      );
    }
}
