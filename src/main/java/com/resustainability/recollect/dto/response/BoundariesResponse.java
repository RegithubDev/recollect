package com.resustainability.recollect.dto.response;

import java.util.Set;

public record BoundariesResponse(
		Set<Long> scrapRegions,
		Set<Long> wards
) {}
