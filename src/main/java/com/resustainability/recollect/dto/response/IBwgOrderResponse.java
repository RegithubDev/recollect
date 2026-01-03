package com.resustainability.recollect.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IBwgOrderResponse {
    Long getId();
    String getOrderCode();
    LocalDateTime getOrderDate();
    LocalDate getScheduleDate();
    String getOrderStatus();
    Boolean getDueSettled();
    String getPreferredPaymentMethod();
    
    Long getClientId();
    String getClientName();
    String getPhoneNumber();

    Long getProviderId();
    String getProviderName();

    Long getVehicleId();
    String getVehicleName();
    String getVehicleNumber();
    
    Long getDistrictId();
    String getDistrictName();

    Long getStateId();
    String getStateName();
    
    Long getScrapRegionId();
    String getScrapRegionName();

    String getComment();
}
