package com.resustainability.recollect.dto.response;

import java.math.BigDecimal;

public interface IWasteBagResponse {
    Long getId();
    String getBagSize();
    BigDecimal getBagPrice();
    Double getBagCgst();
    Double getBagSgst();
    Boolean getIsActive();
    Boolean getIsBwg();
    Long getStateId();
    String getStateName();
    Long getCountryId();
    String getCountryName();
}
