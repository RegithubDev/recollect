package com.resustainability.aakri.commons;

import com.resustainability.aakri.dto.commons.RequestBodyValidator;
import com.resustainability.aakri.exception.InvalidDataException;
import com.resustainability.aakri.exception.ResourceNotFoundException;

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

    public static void validateName(String name) {
        validateLength(name, Default.MIN_DEFAULT_LENGTH, Default.MAX_DEFAULT_LENGTH, "Name");
    }

    public static void validateEmail(String email) {
        validateLength(email, Default.MIN_DEFAULT_LENGTH, Default.MAX_DEFAULT_LENGTH, "Email");
    }

    public static void validatePhone(String mobile) {
        validateNumeric(mobile, "Phone number");
        validateLength(mobile, Default.MIN_PHONE_LENGTH, Default.MAX_PHONE_LENGTH, "Phone number");
    }

    public static void validatePassword(String password) {
        validateLength(password, Default.MIN_PASSWORD_LENGTH, Default.MAX_PASSWORD_LENGTH, "Password");
    }

    public static void validateOtp(String otp) {
        validateNumeric(otp, "OTP");
        validateLength(otp, Default.MIN_MAX_OTP_LENGTH, Default.MIN_MAX_OTP_LENGTH, "OTP");
    }
}
