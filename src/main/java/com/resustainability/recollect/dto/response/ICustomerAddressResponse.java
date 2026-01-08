package com.resustainability.recollect.dto.response;

public interface ICustomerAddressResponse {
    Long getId();

    Boolean getIsScrapService();
    Boolean getIsScrapLocationActive();
    Boolean getIsBioWasteService();
    Boolean getIsBioWasteLocationActive();

    String getResidenceType();
    String getResidenceDetails();
    String getLandmark();
    String getLatitude();
    String getLongitude();

    Boolean getIsDeleted();

    Long getCustomerId();
    String getFullName();
    String getPhoneNumber();

    Long getScrapRegionId();
    String getScrapRegionName();

    Long getDistrictId();
    String getDistrictName();
    String getDistrictCode();

    Long getStateId();
    String getStateName();
    String getStateCode();

    Long getWardId();
    Integer getWardNo();
    String getWardName();

    Long getLocalBodyId();
    String getLocalBodyName();
}
