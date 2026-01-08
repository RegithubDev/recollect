package com.resustainability.recollect.dto.response;

import java.util.List;

public record BoundariesResponse(
		List<IScrapRegionResponse> scrapRegions,
		List<ILocalBodyResponse> localBodies
) {}
