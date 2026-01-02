package com.resustainability.recollect.dto.response;

import java.time.LocalDate;

public interface ILocalBodyLimitResponse {
	Long getId();
	Long getLocalBodyId();
	LocalDate getDate();
	Integer getLimit();
	Integer getRemainingSlots();
}
