package com.resustainability.recollect.exception;

import org.springframework.http.HttpStatus;

public class BadCredentialsException extends BaseException {
    public BadCredentialsException() {
        super(HttpStatus.UNAUTHORIZED, "Bad credentials");
    }
}
