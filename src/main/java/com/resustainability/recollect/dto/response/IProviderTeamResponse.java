package com.resustainability.recollect.dto.response;

import java.time.LocalDateTime;

public interface IProviderTeamResponse {

    Long getId();
    String getTeamName();
    Boolean getIsActive();
    Boolean getIsDeleted();
    LocalDateTime getCreatedAt();
}
