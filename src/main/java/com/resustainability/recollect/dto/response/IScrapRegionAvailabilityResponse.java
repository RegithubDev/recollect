package com.resustainability.recollect.dto.response;

import java.time.LocalDate;

public interface IScrapRegionAvailabilityResponse {
	Long getId();
	LocalDate getDate();
	Integer getLimit();
	Integer getRemainingSlots();
}
