package com.resustainability.recollect.dto.response;

public interface IOrderCartItemResponse {
    Long getId();
    String getOrderType();
    Double getWeight();
    Double getPrice();
    Double getTotalPrice();

    Long getTypeId();
    String getTypeName();
    String getTypeIcon();
    Boolean getTypeIsActive();
    Double getTypePrice();
}
