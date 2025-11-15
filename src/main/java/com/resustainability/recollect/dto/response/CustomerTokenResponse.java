package com.resustainability.recollect.dto.response;

public record CustomerTokenResponse(
        boolean isExisting,
        boolean isActive,
        String accessToken,
        String refreshToken,
        String fullName,
        String email,
        String userType
) {}
