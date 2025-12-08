package com.resustainability.recollect.dto.response;

public interface IServiceCategoryResponse {
    Long getId();
    String getName();
    String getTitle();
    String getSubtitle();
    String getIcon();
    Boolean getIsActive();
    Boolean getIsDisabled();
    String getCategoryUrl();
    String getOrderUrl();
}
