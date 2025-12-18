package com.resustainability.recollect.controller;

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
import com.resustainability.recollect.dto.request.AddAssignedVehicleRequest;
import com.resustainability.recollect.dto.request.UpdateAssignedVehicleRequest;
import com.resustainability.recollect.dto.response.IAssignedVehicleResponse;
import com.resustainability.recollect.service.AssignedVehicleService;

@RestController
@RequestMapping("/api/v1/assigned-vehicle")
@PreAuthorize("hasRole('ADMIN')")
public class AssignedVehicleController {

    private final AssignedVehicleService service;

    @Autowired
    public AssignedVehicleController(AssignedVehicleService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public APIResponse<Pager<IAssignedVehicleResponse>> list(
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(service.list(searchCriteria));
    }

    @GetMapping("/details/{assingedvehicleId}")
    public APIResponse<IAssignedVehicleResponse> getById(@PathVariable Long assingedvehicleId) {
        return new APIResponse<>(service.getById(assingedvehicleId));
    }

    @PostMapping("/add")
    public APIResponse<Long> add(@RequestBody AddAssignedVehicleRequest request) {
        return new APIResponse<>(
                Default.SUCCESS_ADD_ASSIGNED_VEHICLE,
                service.add(request)
        );
    }

    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody UpdateAssignedVehicleRequest request) {
        service.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_ASSIGNED_VEHICLE);
    }

    @DeleteMapping("/delete/{assingedvehicleId}")
    public APIResponse<Void> delete(@PathVariable Long assingedvehicleId) {
        service.deleteById(assingedvehicleId);
        return new APIResponse<>(Default.SUCCESS_DELETE_ASSIGNED_VEHICLE);
    }
}
