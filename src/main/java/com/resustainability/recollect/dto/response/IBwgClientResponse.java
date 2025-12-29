package com.resustainability.recollect.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IBwgClientResponse {

    Long getId();
    String getFullName();
    String getEmail();
    String getPhoneNumber();
    String getContract();
    LocalDateTime getDateJoined();

    Boolean getIsActive();
    Boolean getIsDeleted();
    Boolean getBioOrder();
    Boolean getScrapOrder();

    String getAddress();
    Integer getFamilyNumber();

    Double getClientPrice();
    Double getClientCgst();
    Double getClientSgst();

    String getVerificationStatus();

   
    Long getDistrictId();
    String getDistrictName();

    Long getStateId();
    String getStateName();

    Long getWardId();
    String getWardName();

    Long getScrapRegionId();
    String getScrapRegionName();

    String getAlternateNumber();
    String getClientCategory();
    String getRemark();
    String getServiceType();

    LocalDate getContractEndDate();
    LocalDate getContractStartDate();

    Long getApproveRequestId();
    Boolean getRequestApproved();

    String getGstName();
    String getGstNo();
    String getAccountNumber();
    String getIfscCode();

    String getCollectionFrequency();
    Boolean getMonthlyContract();
}
