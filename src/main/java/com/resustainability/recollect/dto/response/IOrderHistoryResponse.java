package com.resustainability.recollect.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IOrderHistoryResponse {
	Long getId();
	Long getCustomerId();
	String getFullName();
	String getCustomerPhoneNumber();
	String getCode();
	String getType();
	LocalDate getScheduleDate();
	LocalDateTime getOrderDate(); 
	String getStatus();

	Long getScrapOrderId();
	Long getBioWasteOrderId();

	Long getDistrictId();
	String getDistrictName();
	String getDistrictCode();

	Long getScrapRegionId();
	String getScrapRegionName();

	Long getWardId();
	Integer getWardNo();
	String getWardName();

	Long getLocalBodyId();
	String getLocalBodyName();

	String getVehicle();
	Long getProviderId();
	String getProviderName();
	String getPlatform();
	String getProviderPhoneNumber();
	

	Long getAddressId();
	String getResidenceType();
	String getResidenceDetails();
	String getLandmark();
	String getLatitude();
	String getLongitude();
	Boolean getIsAddressDeleted();
	String getScrapBillType();
	String getDHBillType();
}
