package com.resustainability.recollect.dto.response;

public record TokenResponse(
		boolean isActive,
        String token,
        String fullName,
        String email,
        String userType,
        long id,
        String phoneNumber,
        String platform,
		String fcmToken,
		String deviceId
) {}
