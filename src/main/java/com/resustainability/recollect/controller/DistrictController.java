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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddDistrictRequest;
import com.resustainability.recollect.dto.request.UpdateDistrictRequest;
import com.resustainability.recollect.dto.response.IDestrictResponse;
import com.resustainability.recollect.service.DistrictService;

@RestController
@RequestMapping("/recollect/v1/district")
@PreAuthorize("hasRole('ADMIN')")
public class DistrictController {
	
	 private final DistrictService districtService;

	    @Autowired
	    public DistrictController(DistrictService districtService) {
	        this.districtService = districtService;
	    }

	    @GetMapping("/list")
	    public APIResponse<Pager<IDestrictResponse>> list(
	            @RequestParam(required = false) Long stateId,
	            @RequestParam(required = false) Long countryId,
	            @ModelAttribute SearchCriteria searchCriteria
	    ) {
	        return new APIResponse<>(
	                districtService.list(stateId, countryId, searchCriteria)
	        );
	    }
	    

	    @GetMapping("/details/{districtId}")
	    public APIResponse<IDestrictResponse> getById(
	            @PathVariable Long districtId
	    ) {
	        return new APIResponse<>(
	                districtService.getById(districtId)
	        );
	    }

	    @PostMapping("/add")
	    public APIResponse<Long> add(
	            @RequestBody(required = false) AddDistrictRequest request
	    ) {
        return new APIResponse<>(
	                Default.SUCCESS_ADD_DISTRICT,
	                districtService.add(request)
	        );
	    }

	    @PutMapping("/update")
	    public APIResponse<Void> update(
	            @RequestBody(required = false) UpdateDistrictRequest request
	    ) {
	        districtService.update(request);
	        return new APIResponse<>(Default.SUCCESS_UPDATE_DISTRICT);
	    }

	    @DeleteMapping("/delete/{districtId}")
	    public APIResponse<Void> deleteById(
	            @PathVariable Long districtId
	    ) {
	        districtService.deleteById(districtId, true);
	        return new APIResponse<>(Default.SUCCESS_DELETE_DISTRICT);
	    }
	    
	    
	    @DeleteMapping("/un-delete/{districtId}")
	    public APIResponse<Void> undeleteById(
	            @PathVariable Long districtId
	    ) {
	        districtService.deleteById(districtId, false);
	        return new APIResponse<>(Default.SUCCESS_UNDELETE_DISTRICT);
	    }
	    

}
