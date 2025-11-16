package com.resustainability.recollect.exception;

import org.springframework.http.HttpStatus;

public class InvalidSessionException extends BaseException {
    public InvalidSessionException() {
        super(HttpStatus.FORBIDDEN, "Session ended: Your session has expired or was terminated. Please sign in to continue.");
    }
}
