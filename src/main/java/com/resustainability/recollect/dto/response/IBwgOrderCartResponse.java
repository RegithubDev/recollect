package com.resustainability.recollect.dto.response;

public interface IBwgOrderCartResponse {

    Long getId();
    Long getOrderId();
   // String getOrderCode();
    Long getScrapTypeId();
    String getScrapName();
    Double getScrapWeight();
    Double getScrapPrice();
    Double getScrapGst();
    String getScrapHsn();
    Double getTotalPrice();
    Long getBioWasteTypeId();
    String getBiowasteName();
    
}
