package com.resustainability.recollect.dto.response;

import java.util.Set;

public record ProviderCashCollectionResponse(
        Long id,
        String code,
        String fullName,
        Double scrapCashInHand,
        Double bioCashInHand,
        Double bwgScrapCashInHand,
        Double bwgBioCashInHand,
        Double totalCashInHand,
        Set<String> districts
) {}
