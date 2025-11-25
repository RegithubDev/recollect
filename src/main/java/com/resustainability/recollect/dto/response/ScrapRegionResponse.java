package com.resustainability.recollect.dto.response;

import java.util.List;

public record ScrapRegionResponse (
		IScrapRegionResponse details,
		List<IScrapRegionAvailabilityResponse> availability
) {}
