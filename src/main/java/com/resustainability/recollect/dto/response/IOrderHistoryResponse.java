package com.resustainability.recollect.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IOrderHistoryResponse {
	Long getId();
	Long getCustomerId();
	String getFullName();
	String getCode();
	String getType();
	LocalDate getScheduleDate();
	LocalDateTime getOrderDate();
	String getStatus();

	Long getScrapOrderId();
	Long getBioWasteOrderId();

	Long getScrapRegionId();
}
