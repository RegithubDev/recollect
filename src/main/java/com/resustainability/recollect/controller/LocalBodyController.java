package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddLocalBodyRequest;
import com.resustainability.recollect.dto.request.UpdateLocalBodyRequest;
import com.resustainability.recollect.dto.response.ILocalBodyResponse;
import com.resustainability.recollect.dto.response.ILocalBodyResponseByDistrictId;
import com.resustainability.recollect.service.LocalBodyService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/localbody")
//@PreAuthorize("hasRole('ADMIN')")
public class LocalBodyController {

    private final LocalBodyService localBodyService;

    public LocalBodyController(LocalBodyService localBodyService) {
        this.localBodyService = localBodyService;
    }

    @GetMapping("/list")
    public APIResponse<Pager<ILocalBodyResponse>> list(
            @RequestParam(required = false) Long districtId,
            @RequestParam(required = false) Long stateId,
            @RequestParam(required = false) Long countryId,
            @ModelAttribute SearchCriteria criteria
    ) {
        return new APIResponse<>(
                localBodyService.list(districtId, stateId, countryId, criteria)
        );
    }

    @GetMapping("/details/{id}")
    public APIResponse<ILocalBodyResponse> getById(@PathVariable Long id) {
        return new APIResponse<>(localBodyService.getById(id));
    }
    
    @GetMapping("/details-localbody/{districtId}")
    public APIResponse<Pager<ILocalBodyResponseByDistrictId>> listByDistrict(
            @PathVariable Long districtId,
            @ModelAttribute SearchCriteria criteria
    ) {
        return new APIResponse<>(
                localBodyService.listByDistrict(districtId, criteria)
        );
    }
  

    @PostMapping("/add")
    public APIResponse<Long> add(@RequestBody AddLocalBodyRequest request) {
        return new APIResponse<>(Default.SUCCESS_ADD_LOCAL_BODY, localBodyService.add(request));
    }

    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody UpdateLocalBodyRequest request) {
        localBodyService.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_LOCAL_BODY);
    }

    @DeleteMapping("/delete/{id}")
    public APIResponse<Void> delete(@PathVariable Long id) {
        localBodyService.deleteById(id, true);
        return new APIResponse<>(Default.SUCCESS_DELETE_LOCAL_BODY);
    }

    @DeleteMapping("/un-delete/{id}")
    public APIResponse<Void> undelete(@PathVariable Long id) {
        localBodyService.deleteById(id, false);
        return new APIResponse<>(Default.SUCCESS_UNDELETE_LOCAL_BODY);
    }
}
