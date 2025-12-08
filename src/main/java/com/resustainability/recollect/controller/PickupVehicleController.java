package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddPickupVehicleRequest;
import com.resustainability.recollect.dto.request.UpdatePickupVehicleRequest;
import com.resustainability.recollect.dto.response.IPickupVehicleResponse;
import com.resustainability.recollect.service.PickupVehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/pickup-vehicle")
@PreAuthorize("hasRole('ADMIN')")
public class PickupVehicleController {

    private final PickupVehicleService pickupVehicleService;

    @Autowired
    public PickupVehicleController(PickupVehicleService pickupVehicleService) {
        this.pickupVehicleService = pickupVehicleService;
    }

    @GetMapping("/list")
    public APIResponse<Pager<IPickupVehicleResponse>> list(
            @RequestParam(required = false) Long stateId,
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(pickupVehicleService.list(stateId, searchCriteria));
    }

    @GetMapping("/details/{id}")
    public APIResponse<IPickupVehicleResponse> getById(@PathVariable Long id) {
        return new APIResponse<>(pickupVehicleService.getById(id));
    }

    @PostMapping("/add")
    public APIResponse<Long> add(@RequestBody AddPickupVehicleRequest request) {
        return new APIResponse<>(
                Default.SUCCESS_ADD_PICKUP_VEHICLE,
                pickupVehicleService.add(request)
        );
    }

    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody UpdatePickupVehicleRequest request) {
        pickupVehicleService.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_PICKUP_VEHICLE);
    }

    @DeleteMapping("/delete/{id}")
    public APIResponse<Void> deleteById(@PathVariable Long id) {
        pickupVehicleService.delete(id, true);
        return new APIResponse<>(Default.SUCCESS_DELETE_PICKUP_VEHICLE);
    }

    @DeleteMapping("/un-delete/{id}")
    public APIResponse<Void> undelete(@PathVariable Long id) {
        pickupVehicleService.delete(id, false);
        return new APIResponse<>(Default.SUCCESS_UNDELETE_PICKUP_VEHICLE);
    }

    @PostMapping(value = "/upload-file/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public APIResponse<String> uploadImage(
            @PathVariable Long id,
            @RequestParam MultipartFile file) {

        return new APIResponse<>(
                pickupVehicleService.uploadImage(id, file),
                Default.SUCCESS,
                null
        );
    }

    @DeleteMapping("/remove-file/{id}")
    public APIResponse<String> removeImage(@PathVariable Long id) {
        return new APIResponse<>(
                pickupVehicleService.removeImage(id),
                Default.SUCCESS,
                null
        );
    }
}
