package com.resustainability.recollect.commons;

import com.resustainability.recollect.dto.commons.RequestBodyValidator;
import com.resustainability.recollect.exception.InvalidDataException;
import com.resustainability.recollect.exception.ResourceNotFoundException;

public class ValidationUtils {
    private ValidationUtils() {}

    public static void validateRequired(String value, String fieldName) {
        if (StringUtils.isBlank(value)) {
            throw new InvalidDataException(fieldName + " is required");
        }
    }

    public static void validateLength(String value, int min, int max, String fieldName) {
        validateRequired(value, fieldName);
        int length = value.length();
        if (length < min || length > max) {
            throw new InvalidDataException(
                    String.format("%s must be between %d and %d characters.", fieldName, min, max)
            );
        }
    }

    public static void validateNumeric(String value, String fieldName) {
        validateRequired(value, fieldName);
        if (!StringUtils.isNumeric(value)) {
            throw new InvalidDataException(
                    String.format("%s should be numeric. No letters or symbols allowed.", fieldName)
            );
        }
    }

    public static void validateNonNegative(Long value, String fieldName) {
        if (value == null || value < 0) {
            throw new InvalidDataException(fieldName + " is required and must be non-negative");
        }
    }

    public static void validateRequired(Object value, String fieldName) {
        if (value == null) {
            throw new InvalidDataException(fieldName + " is required");
        }
    }

    public static void validateRequestBody(RequestBodyValidator request) {
        if (request == null) {
            throw new ResourceNotFoundException(Default.ERROR_EMPTY_REQUEST_BODY);
        }
        request.validate();
    }

    public static void validateUserId(Long id) {
        validateNonNegative(id, "User ID");
    }

    public static void validateName(String value) {
        validateLength(value, Default.MIN_DEFAULT_LENGTH, Default.MAX_DEFAULT_LENGTH, "Name");
    }

    public static void validateEmail(String value) {
        validateLength(value, Default.MIN_DEFAULT_LENGTH, Default.MAX_DEFAULT_LENGTH, "Email");
    }

    public static void validatePhone(String value) {
//        validateNumeric(value, "Phone number");
        validateLength(value, Default.MIN_PHONE_LENGTH, Default.MAX_PHONE_LENGTH, "Phone number");
    }
}
