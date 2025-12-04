package com.resustainability.recollect.dto.response;

import java.time.LocalDateTime;

public interface IProviderTeamAllotedWardResponse {

    Long getId();

    Boolean getMonday();
    Boolean getTuesday();
    Boolean getWednesday();
    Boolean getThursday();
    Boolean getFriday();
    Boolean getSaturday();
    Boolean getSunday();

    LocalDateTime getCreatedAt();

    Long getTeamId();
    String getTeamName();

    Long getWardId();
    String getWardName();
}
