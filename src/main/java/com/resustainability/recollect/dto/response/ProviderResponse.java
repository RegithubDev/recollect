package com.resustainability.recollect.dto.response;

import java.util.Set;

public record ProviderResponse(
        Long id,
        String code,
        String fullName,
        String phoneNumber,
        Long stateId,
        String stateName,
        String stateCode,
        String providerType,
        String password,
        boolean scrapPickup,
        boolean biowastePickup,
        boolean bwgBioPickup,
        boolean bwgScrapPickup,
        boolean isActive,
        Integer orderPickupLimit,
        Integer cashLimit,
        Integer addOrderLimit,
        Integer pickupWeightLimit,
        Set<String> districts
) {}
