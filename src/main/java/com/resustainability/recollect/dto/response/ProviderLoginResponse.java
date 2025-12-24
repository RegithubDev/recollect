package com.resustainability.recollect.dto.response;

public record ProviderLoginResponse(

        // Tokens
        String access_token,
        String refresh_token,

        // User details
        String full_name,
        String phone_number,

        // State info
        Long state_id,
        String state_name,

        // Vehicle details
        String scrap_vehicle,
        String bio_vehicle,
        String bwg_scrap_vehicle,
        String bwg_bio_vehicle,

        // Role & permissions
        String user_role,
        String user_code,
        boolean scrap_allowed,
        boolean bio_allowed,
        boolean bwg_scrap_allowed,
        boolean bwg_bio_allowed,
        boolean is_admin,

        // Profile
        String profile_pic
) {}
