package com.resustainability.recollect.dto.response;

public interface IOrderCancelReasonResponse {
	Long getId();
	String getReason();
	String getOrderType();
	Boolean getIsActive();
}
