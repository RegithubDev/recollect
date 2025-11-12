package com.resustainability.aakri.dto.response;

public record CustomerTokenResponse(
        String message,
        boolean is_existing,
        boolean is_active,
        String access_token,
        String refresh_token,
        String full_name,
        String email,
        String user_type
) {}
