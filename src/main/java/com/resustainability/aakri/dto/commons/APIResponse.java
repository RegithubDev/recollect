package com.resustainability.aakri.dto.commons;

public record APIResponse<T> (
        T data,
        String message,
        String error
) {
    public APIResponse(T data) {
        this(data, null, null);
    }

    public APIResponse(String message) {
        this(null, message, null);
    }

    public APIResponse(String error, T data) {
        this(data, null, error);
    }

    public APIResponse(T data, String message) {
        this(data, message, null);
    }
}