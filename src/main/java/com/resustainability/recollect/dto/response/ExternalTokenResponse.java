package com.resustainability.recollect.dto.response;

public record ExternalTokenResponse(
		boolean valid,
        String message,
        ValidData data
) {
	public record ValidData(
			String uid,
			String role,
			String email,
			String mobile
	) {}
}
