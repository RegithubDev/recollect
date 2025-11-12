package com.resustainability.aakri.exception;

import org.springframework.http.HttpStatus;

public class DataSyncInProgressException extends BaseException {
    public DataSyncInProgressException() {
        super(HttpStatus.SERVICE_UNAVAILABLE, "Hang tight! We're syncing data from data source. Please try again shortly.");
    }
}
