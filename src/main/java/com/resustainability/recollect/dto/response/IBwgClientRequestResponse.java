package com.resustainability.recollect.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IBwgClientRequestResponse {

    Long getId();
    String getFullName();
    String getPhoneNumber();
    String getAlternateNumber();
    String getServiceType();
    String getAddress();
    String getLandmark();
    String getHouseName();
    String getHouseNumber();
    String getClientCategory();
    LocalDate getAppointmentDate();
    String getRemark();
    String getCallCenterRemark();
    String getVerificationStatus();
    Boolean getBioPickup();
    Boolean getScrapPickup();
    String getCollectionFrequency();
    Integer getFamilyNumber();
    Boolean getIsConfirmed();
    String getAccountNumber();
    String getIfscCode();
    LocalDateTime getCreatedAt();

    Long getStateId();
    String getStateName();
}
