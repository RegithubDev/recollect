package com.resustainability.recollect.dto.response;

public interface IScrapCategoryTypeResponse {
    Long getCategoryId();
    String getCategoryName();
    String getSubcategoryName();
    String getCategoryIcon();
    Boolean getCategoryIsActive();

    Long getTypeId();
    String getTypeName();
    String getTypeIcon();
    Boolean getTypeIsPayable();
    Boolean getTypeIsKg();
    Boolean getTypeIsActive();

    Boolean getPriceIsActive();
    Double getPrice();
    Double getCgst();
    Double getSgst();
}
