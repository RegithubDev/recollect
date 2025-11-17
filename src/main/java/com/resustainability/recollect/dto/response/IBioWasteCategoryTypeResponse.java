package com.resustainability.recollect.dto.response;

public interface IBioWasteCategoryTypeResponse {
    Long getCategoryId();
    String getCategoryName();
    String getCategoryIcon();
    Boolean getCategoryIsActive();

    Long getTypeId();
    String getTypeName();
    String getTypeIcon();
    Boolean getTypeIsActive();
}
