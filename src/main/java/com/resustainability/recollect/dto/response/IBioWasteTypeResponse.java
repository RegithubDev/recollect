package com.resustainability.recollect.dto.response;

public interface IBioWasteTypeResponse {
    Long getId();
    String getBiowasteName();
    String getImage();
    Boolean getIsActive();
    Long getCategoryId();
    String getCategoryName();
}
