package com.resustainability.aakri.exception;

import org.springframework.http.HttpStatus;

public class BadCredentialsException extends BaseException {
    public BadCredentialsException() {
        super(HttpStatus.UNAUTHORIZED, "Bad credentials");
    }
}
