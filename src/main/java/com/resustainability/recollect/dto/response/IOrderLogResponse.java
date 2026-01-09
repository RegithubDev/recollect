package com.resustainability.recollect.dto.response;

import java.time.LocalDateTime;

public interface IOrderLogResponse {
    Long getId();
    Long getAdminId();
    Long getClientId();
    Long getOrderId();
    Long getProviderId();
    Long getCustomerId();

    String getDoneBy();
    String getDescription();
    LocalDateTime getCreatedAt();
}
