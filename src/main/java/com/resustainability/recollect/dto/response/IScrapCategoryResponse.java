package com.resustainability.recollect.dto.response;

public interface IScrapCategoryResponse {
    Long getId();
    String getCategoryName();
    String getSubcategoryName();
    String getImage();
    Boolean getIsActive();
    String getHsnCode();
}
