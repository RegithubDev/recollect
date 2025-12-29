package com.resustainability.recollect.dto.response;

public interface IBwgBagPriceResponse {

    Long getId();
    String getBagSize();
    Double getBagPrice();
    Double getBagCgst();
    Double getBagSgst();
    Boolean getIsActive();

    Long getClientId();
    String getClientName();
}
