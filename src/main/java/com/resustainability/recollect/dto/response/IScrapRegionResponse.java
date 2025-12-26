package com.resustainability.recollect.dto.response;

public interface IScrapRegionResponse {
	Long getId();
	String getName();
	String getCurrentWeekday();
	String getNextWeekday();
	Boolean getIsActive();
	Boolean getIsDeleted();

	Long getDistrictId();
	String getDistrictName();
	String getDistrictCode();

	Long getStateId();
	String getStateName();
	String getStateCode();

	Long getCountryId();
	String getCountryName();
}
