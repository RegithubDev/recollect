package com.resustainability.recollect.dto.request;

public record ScrapDistrictPriceRequest(
        Long districtId,
        Double scrapPrice,
        Double scrapCgst,
        Double scrapSgst
) {
}
