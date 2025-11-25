package com.resustainability.recollect.dto.response;

public interface IDistrictResponse {
	Long getId();
	String getName();
	String getCode();
	Boolean getIsActive();
	Boolean getIsDeleted();

	Long getStateId();
	String getStateName();

	Long getCountryId();
	String getCountryName();
}
