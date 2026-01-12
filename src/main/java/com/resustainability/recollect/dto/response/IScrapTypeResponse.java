package com.resustainability.recollect.dto.response;



public interface IScrapTypeResponse {
    Long getId();
    String getScrapName();
    String getImage();
    Boolean getIsPayable();
    Boolean getIsKg();
    Boolean getIsActive();
    Long getCategoryId();
    String getCategoryName();
    
}


