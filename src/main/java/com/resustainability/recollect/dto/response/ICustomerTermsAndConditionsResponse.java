package com.resustainability.recollect.dto.response;

import java.time.LocalDateTime;

public interface ICustomerTermsAndConditionsResponse {
    Long getId();
    Boolean getIsOptional();
    LocalDateTime getSignedDate();

    Long getCustomerId();
    String getFullName();
    String getPhoneNumber();
}
