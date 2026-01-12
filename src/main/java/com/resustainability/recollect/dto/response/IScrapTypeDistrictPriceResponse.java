package com.resustainability.recollect.dto.response;

public interface IScrapTypeDistrictPriceResponse {

    Long getDistrictId();
    String getDistrictName();

    Double getScrapPrice();
    Double getScrapCgst();
    Double getScrapSgst();

    Boolean getIsActive();
}
