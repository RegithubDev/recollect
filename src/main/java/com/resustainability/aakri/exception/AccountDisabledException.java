package com.resustainability.aakri.exception;

import org.springframework.http.HttpStatus;

public class AccountDisabledException extends BaseException {
    public AccountDisabledException(String errorMessage) {
        super(HttpStatus.FORBIDDEN, errorMessage);
    }
}
