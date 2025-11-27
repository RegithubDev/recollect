package com.resustainability.recollect.commons;

import com.resustainability.recollect.dto.commons.RequestBodyValidator;
import com.resustainability.recollect.dto.payload.PayloadScrapRegionAvailability;
import com.resustainability.recollect.exception.InvalidDataException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BooleanSupplier;

public class ValidationUtils {
    private ValidationUtils() {}

    public static void validateRequired(String value, String fieldName) {
        if (StringUtils.isBlank(value)) {
            throw new InvalidDataException(fieldName + " is required");
        }
    }

    public static void validateMultipartSize(MultipartFile file, long size) {
        validateMultipart(file);
        if (file.getSize() > size) {
            throw new IllegalArgumentException("File too large, try uploading " + FileUtils.byteCountToDisplaySize(size));
        }
    }

    public static void validateMultipart(MultipartFile file) {
        if (null == file || file.isEmpty()) {
            throw new InvalidDataException(Default.ERROR_EMPTY_MULTIPART);
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

    public static void validateNonNegative(Integer value, String fieldName) {
        if (value == null || value < 0) {
            throw new InvalidDataException(fieldName + " is required and must be non-negative");
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

    public static void validateId(Long id) {
        validateNonNegative(id, "ID");
    }

    public static void validateUserId(Long id) {
        validateNonNegative(id, "User ID");
    }

    public static void validateDistrictId(Long id) {
        validateNonNegative(id, "District ID");
    }

    public static void validateScrapRegionAvailability(List<PayloadScrapRegionAvailability> availability) {
        if (CollectionUtils.isBlank(availability)) {
            throw new InvalidDataException("Provide availability pickup dates");
        }
    }

    public static void validateCode(String value) {
        validateLength(value, Default.MIN_DEFAULT_LENGTH, Default.MAX_10_LENGTH, "Code");
    }

    public static void validateLatitude(String value) {
        validateLength(value, Default.MIN_DEFAULT_LENGTH, Default.MAX_100_LENGTH, "Latitude");
    }

    public static void validateLongitude(String value) {
        validateLength(value, Default.MIN_DEFAULT_LENGTH, Default.MAX_100_LENGTH, "Longitude");
    }

    public static void validateResidenceType(String value) {
        validateLength(value, Default.MIN_DEFAULT_LENGTH, Default.MAX_20_LENGTH, "Residence Type");
    }

    public static void validateResidenceDetails(String value) {
        validateLength(value, Default.MIN_DEFAULT_LENGTH, Default.MAX_500_LENGTH, "Residence Details");
    }

    public static void validateLandmark(String value) {
        validateLength(value, Default.MIN_DEFAULT_LENGTH, Default.MAX_500_LENGTH, "Landmark");
    }

    public static void validateName(String value) {
        validateLength(value, Default.MIN_DEFAULT_LENGTH, Default.MAX_50_LENGTH, "Name");
    }

    public static void validatePhone(String value) {
//        validateNumeric(value, "Phone number");
        validateLength(value, Default.MIN_PHONE_LENGTH, Default.MAX_PHONE_LENGTH, "Phone number");
    }

    public static void validateEmail(String value) {
        validateLength(value, Default.MIN_DEFAULT_LENGTH, Default.MAX_DEFAULT_LENGTH, "Email");
    }

    public static void validateUsername(String value) {
        validateLength(value, 0, Default.MAX_150_LENGTH, "Username");
    }

    public static void validatePassword(String value) {
        validateRequired(value, "Password");
    }

    public static void validateUserType(String value) {
        validateLength(value, Default.MIN_DEFAULT_LENGTH, Default.MAX_20_LENGTH, "User type");
    }

    public static void validatePlatform(String value) {
        validateLength(value, Default.MIN_DEFAULT_LENGTH, Default.MAX_20_LENGTH, "Platform");
    }

    public static void validateDateJoined(LocalDateTime value) {
        validateRequired(value, "Date joined");
    }

    public static void validateUserActiveStatus(BooleanSupplier isActive) {
        if (!Boolean.TRUE.equals(isActive.getAsBoolean())) {
            throw new AuthenticationServiceException(Default.ERROR_ACCOUNT_DISABLED);
        }
    }
}
