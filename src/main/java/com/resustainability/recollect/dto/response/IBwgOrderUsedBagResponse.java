package com.resustainability.recollect.dto.response;

public interface IBwgOrderUsedBagResponse {

    Long getId();
    Integer getNumberOfBags();
    Double getTotalBagPrice();
    Double getFinalPrice();

    Double getCgstPrice();
    Double getSgstPrice();

    Long getBagId();
    String getBagSize();

    Long getOrderId();
}
