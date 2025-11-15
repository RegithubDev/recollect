package com.resustainability.recollect.dto.response;

import java.time.LocalDateTime;

public interface ICustomerResponse {
    Long getId();
    LocalDateTime getLastLogin();
    Boolean getIsSuperuser();
    Boolean getIsStaff();
    Boolean getIsActive();
    LocalDateTime getDateJoined();
    String getFullName();
    String getOtp();
    String getPhoneNumber();
    String getEmail();
    String getUserType();
    String getPlatform();
    Boolean getIsDeleted();

    Long getDistrictId();
    String getDistrictName();
    String getDistrictCode();

    Long getStateId();
    String getStateName();
    String getStateCode();

    Long getScrapRegionId();
    String getScrapRegionName();

    Long getWardId();
    Integer getWardNo();
    String getWardName();

    Long getLocalBodyId();
    String getLocalBodyName();
}
