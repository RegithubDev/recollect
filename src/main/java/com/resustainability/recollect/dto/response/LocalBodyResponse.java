package com.resustainability.recollect.dto.response;

import java.util.List;

public record LocalBodyResponse(
		ILocalBodyResponse details,
		List<ILocalBodyLimitResponse> availability
) {}
