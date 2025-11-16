package com.resustainability.recollect.dto.response;

import java.time.LocalDateTime;

public interface IUserContext {
    Long getId();
    String getUsername();
    String getFullName();
    String getPhoneNumber();
    String getEmail();
    String getUserType();
    LocalDateTime getTokenAt();
    Boolean getIsSuperuser();
    Boolean getIsStaff();
    Boolean getIsActive();
    Boolean getIsDeleted();
    Boolean getIsCustomer();
    Boolean getIsAdmin();

    Long getDistrictId();
    Long getStateId();
    Long getScrapRegionId();
    Long getWardId();

    Long getRoleId();
    String getRoleName();
    Boolean getRoleActive();
}
