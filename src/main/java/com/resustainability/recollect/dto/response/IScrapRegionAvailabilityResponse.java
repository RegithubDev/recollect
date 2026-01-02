package com.resustainability.recollect.dto.response;

import java.time.LocalDate;

public interface IScrapRegionAvailabilityResponse {
	Long getId();
	Long getScrapRegionId();
	LocalDate getDate();
	Integer getLimit();
	Integer getRemainingSlots();
}
