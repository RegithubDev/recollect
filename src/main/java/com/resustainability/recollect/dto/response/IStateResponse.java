package com.resustainability.recollect.dto.response;


public interface IStateResponse {
	
	 Long getId();
	 String getName();
	 String getCode();
	 String getIcon();
	 Boolean getIsActive();
	 Boolean getIsDeleted();
	 Long getCountryId();
	 String getCountryName();

}
