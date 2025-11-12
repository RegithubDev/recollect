package com.resustainability.aakri.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class NoContentException extends BaseException {
    public NoContentException() {
        super(HttpStatus.NO_CONTENT, List.of());
    }
    public NoContentException(String errorMessage) {
        super(HttpStatus.NO_CONTENT, errorMessage);
    }
}
