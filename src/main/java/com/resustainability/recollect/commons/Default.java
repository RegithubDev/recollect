package com.resustainability.recollect.commons;

public class Default {

    private Default() {}

    public static final String EMPTY = "";
    public static final String ALL = "all";
    public static final String SUCCESS = "Success";
    public static final String UNDERSCORE = "_";
    public static final String DELETED_PREFIX = "deleted_";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String HEADER_API_KEY = "x-api-key";
    public static final String HEADER_X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String HEADER_X_REQUEST_ID = "X-Request-ID";
    public static final String HEADER_X_AT_ID = "X-AT-ID";
    public static final String HEADER_SHEETS_CONTENT_TYPE_VALUE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String HEADER_SHEETS_CONTENT_DISPOSITION_VALUE = "attachment; filename=%s.xlsx";
    public static final String ZONE = "Asia/Kolkata";

    public static final String ERROR_INVALID_SHEET_ROW = "Row numbers must be >= 1";
    public static final String ERROR_ACCOUNT_DISABLED = "Your account has been disabled. Please contact support for assistance";
    public static final String ERROR_UNABLE_TO_UPLOAD = "Upload failed. Retry shortly";
    public static final String ERROR_EMPTY_REQUEST_BODY = "Request cannot be empty";
    public static final String ERROR_EMPTY_MULTIPART = "File cannot be empty";
    public static final String ERROR_NOT_FOUND_POSITION = "Position not found";
    public static final String ERROR_NOT_FOUND_DEPARTMENT = "Department not found";
    public static final String ERROR_NOT_FOUND_USER = "User not found";
    public static final String ERROR_NOT_FOUND_COUNTRY = "Country not found";
    public static final String ERROR_NOT_FOUND_DISTRICT = "District not found";
    public static final String ERROR_NOT_FOUND_SCRAP_REGION = "Scrap region not found";
    public static final String ERROR_NOT_FOUND_STATE = "State not found";
    public static final String ERROR_NOT_FOUND_WARD = "Ward not found";
    public static final String ERROR_NOT_FOUND_EMPLOYEE = "Employee not found";
    public static final String ERROR_NOT_FOUND_JOB = "Job not found";
    public static final String ERROR_NOT_FOUND_ORG = "Org not found";
    public static final String ERROR_NOT_FOUND_SITE = "Site not found";
    public static final String ERROR_NOT_PROVIDED_USER = "No User provided";
    public static final String ERROR_NOT_PROVIDED_TXNS = "No Txns provided";
    public static final String ERROR_NOT_PROVIDED_JOB = "No Job provided";

    public static final String SUCCESS_LOGIN = "Login success. Session created.";
    public static final String SUCCESS_LOGOUT = "You're logged out! Session has been terminated.";
    public static final String SUCCESS_REGISTER = "Account created.";
    public static final String SUCCESS_SESSION_INVALIDATED = "Sessions invalidated.";
    public static final String SUCCESS_FORGOT_PASSWORD_REQUEST_INIT = "An email has been sent to your address containing OTP to reset your password.";
    public static final String SUCCESS_FORGOT_PASSWORD_OTP_VERIFY = "OTP verification successful. You may now proceed to update your password.";
    public static final String SUCCESS_FORGOT_PASSWORD_RESET = "Your password has been reset successfully. Please proceed to log in.";
    public static final String SUCCESS_ADD_USER = "User created.";
    public static final String SUCCESS_ADD_COUNTRY = "Country created.";
    public static final String SUCCESS_ADD_TASK = "Task created.";
    public static final String SUCCESS_UPDATE_PROFILE_DETAILS = "Your profile details have been updated successfully.";
    public static final String SUCCESS_UPDATE_USER_DETAILS = "User details have been updated successfully.";
    public static final String SUCCESS_UPDATE_COUNTRY_DETAILS = "Country details have been updated successfully.";
    public static final String SUCCESS_UPDATE_TASK_DETAILS = "Task details have been updated successfully.";
    public static final String SUCCESS_UPDATED_NOTICE = "Notice updated";
    public static final String SUCCESS_UPDATE_USER_STATUS = "User status updated.";
    public static final String SUCCESS_DELETE_USER = "All done! The account's been deleted.";
    public static final String SUCCESS_DELETE_COUNTRY = "All done! The country's been deleted.";
    public static final String SUCCESS_DELETE_TASK = "All done! The task's been deleted.";

    public static final String EXECUTOR_MAIL = "mailTaskExecutor";
    public static final String EXECUTOR_ASYNC = "asyncTaskExecutor";

    public static final String QUERY_SQL_SERVER_RESET_SEED = "DBCC CHECKIDENT ('[%s]', RESEED, 0)";

    public static final long MAX_IMAGE_FILE_SIZE = 5L * 1024L * 1024L; // 5MB

    public static final int MAX_DEFAULT_LENGTH = 250;
    public static final int MIN_DEFAULT_LENGTH = 2;

    public static final int MAX_10_LENGTH = 10;
    public static final int MAX_20_LENGTH = 20;
    public static final int MAX_50_LENGTH = 50;
    public static final int MAX_150_LENGTH = 150;
    public static final int MAX_500_LENGTH = 500;
    public static final int MIN_MAX_OTP_LENGTH = 4;

    public static final int MIN_PHONE_LENGTH = 9;
    public static final int MAX_PHONE_LENGTH = 36;
}
