package com.resustainability.recollect.dto.response;

import java.time.LocalDateTime;

public interface IAdminUserResponse {
    Long getId();
    String getUsername();
    String getFullName();
    String getPhoneNumber();
    String getEmail();
    Boolean getIsSuperuser();
    Boolean getIsStaff();
    Boolean getIsActive();
    Boolean getIsDeleted();
    LocalDateTime getLastLogin();
    LocalDateTime getDateJoined();

    Long getRoleId();
    String getRoleName();
    Boolean getRoleActive();
}
