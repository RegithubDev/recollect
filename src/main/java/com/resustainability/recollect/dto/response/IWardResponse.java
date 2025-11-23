package com.resustainability.recollect.dto.response;

public interface IWardResponse {

    Long getId();
    Integer getWardNo();
    String getWardName();
    String getWardWeekdayCurrent();
    String getWardWeekdayNext();

    Boolean getIsActive();
    Boolean getIsDeleted();

    Long getLocalbodyId();
    String getLocalbodyName();

    Long getDistrictId();
    String getDistrictName();

    Long getStateId();
    String getStateName();

    Long getCountryId();
    String getCountryName();
}
