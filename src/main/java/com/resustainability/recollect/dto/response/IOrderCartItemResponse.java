package com.resustainability.recollect.dto.response;

public interface IOrderCartItemResponse {
    Long getId();
    String getOrderType();
    Double getWeight();
    Double getPrice();
    Double getTotalPrice();
    Double getCapturedWeight();

    Long getTypeId();
    String getTypeName();
    String getTypeIcon();
    Byte getTypeIsActive();
    Double getTypePrice();

    Long getCartId();
}
