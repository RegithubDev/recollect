package com.resustainability.recollect.commons;

public class Default {
    private Default() {}

    public static final String EMPTY = "";
    public static final String ALL = "all";
    public static final String SUCCESS = "Success";
    public static final String UNDERSCORE = "_";
    public static final String DELETED_PREFIX = "deleted_";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String ZONE = "Asia/Kolkata";



    public static final String HEADER_API_KEY = "x-api-key";
    public static final String HEADER_X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String HEADER_X_REQUEST_ID = "X-Request-ID";
    public static final String HEADER_X_AT_ID = "X-AT-ID";
    public static final String HEADER_SHEETS_CONTENT_TYPE_VALUE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String HEADER_SHEETS_CONTENT_DISPOSITION_VALUE = "attachment; filename=%s.xlsx";


    public static final String ERROR_ALREADY_ALLOTED ="This ward is already allotted to the team";
    public static final String ERROR_INVALID_SHEET_ROW = "Row numbers must be >= 1";
    public static final String ERROR_ACCOUNT_DISABLED = "Your account has been disabled. Please contact support for assistance";
    public static final String ERROR_UNABLE_TO_UPLOAD = "Upload failed. Retry shortly";

    public static final String ERROR_EMPTY_REQUEST_BODY = "Request cannot be empty";
    public static final String ERROR_EMPTY_MULTIPART = "File cannot be empty";

    public static final String ERROR_NOT_FOUND_POSITION = "Position not found";
    public static final String ERROR_NOT_FOUND_DEPARTMENT = "Department not found";
    public static final String ERROR_NOT_FOUND_USER = "User not found";
    public static final String ERROR_NOT_FOUND_COUNTRY = "Country not found";
    public static final String ERROR_NOT_FOUND_CUSTOMER_ADDRESS = "Customer address not found";
    public static final String ERROR_NOT_FOUND_ORDER = "Order not found";
    public static final String ERROR_NOT_FOUND_DISTRICT = "District not found";
    public static final String ERROR_NOT_FOUND_LOCAL_BODY_TYPE = "LocalBodyType not found";
    public static final String ERROR_NOT_FOUND_SCRAP_REGION = "Scrap region not found";
    public static final String ERROR_NOT_FOUND_REASON = "Reason not found";
    public static final String ERROR_NOT_FOUND_STATE = "State not found";
    public static final String ERROR_NOT_FOUND_WARD = "Ward not found";
    public static final String ERROR_NOT_FOUND_LOCAL_BODY = "LocalBody not found";
    public static final String ERROR_NOT_FOUND_PROVIDER_ROLE = "Provider Role not found";
    public static final String ERROR_NOT_FOUND_LOCALBODY = "LocalBody not found";
    public static final String ERROR_NOT_FOUND_PROVIDERROLE = "Provider Role not found";
    public static final String ERROR_NOT_FOUND_PROVIDER = "Provider not found with given id";
    public static final String ERROR_NOT_FOUND_PROVIDER_TEAM = "Provider team not found";
    public static final String ERROR_NOT_FOUND_PROVIDER_ORDER_LIMIT = "Provider limit not found";
    public static final String ERROR_NOT_FOUND_PROVIDER_DISTRICT ="Provider District with given Id not available";
    public static final String ERROR_NOT_FOUND_PROVIDER_LOCALBODY ="Provider Localbody not available";
    public static final String ERROR_NOT_FOUND_PROVIDER_WARD ="Porovider Ward details with given id not found";
    public static final String ERROR_NOT_FOUND_PROVIDER_SCRAP_REGION ="Porovider Scrap Region details with given id not found";
    public static final String ERROR_NOT_FOUND_PROVIDER_LOCATION_FLAGS ="Porovider Service location details not found with given id";
    public static final String ERROR_NOT_FOUND_WASTEBAG ="Waste bag details not found with given id";
    public static final String ERROR_NOT_FOUND_EMPLOYEE = "Employee not found";
    public static final String ERROR_NOT_FOUND_JOB = "Job not found";
    public static final String ERROR_NOT_FOUND_ORG = "Org not found";
    public static final String ERROR_NOT_FOUND_SITE = "Site not found";
    public static final String ERROR_NOT_PROVIDED_USER = "No User provided";
    public static final String ERROR_NOT_PROVIDED_TXNS = "No Txns provided";
    public static final String ERROR_NOT_PROVIDED_JOB = "No Job provided";

    public static final String ERROR_LIMIT_EXCEEDED ="Current Limit cannot be greater than Max Limit";
    public static final String ERROR_NOT_FOUND_PROVIDERTEAM ="Porovider Team with given id not found";
    
    public static final String ERROR_NOT_FOUND_PICKUP_VEHICLE ="Pickup Vehile not found";
    
    public static final String ERROR_NOT_FOUND_PICKUPVEHICLEDISTRICT ="Pickup Vehile district details are not found";
    
    public static final String ERROR_NOT_FOUND_PICKUPWEIGHT_LIMIT ="Pickup Weight limit details not found with given id";
   
    public static final String ERROR_NOT_FOUND_ASSIGNED_VEHILE ="Assigned vehicle not found";
    
    public static final String ERROR_NOT_FOUND_BWG_CLIENT_REQUEST ="BWG Client Request not found";
    
    public static final String ERROR_NOT_FOUND_BWG_BAG_PRICE ="BWG bag details not found";
    
    public static final String ERROR_NOT_FOUND_BWG_CLIENT ="BWG Client not found";
    
    public static final String ERROR_NOT_FOUND_BWG_CLIENT_STATE ="State Not mapped to client";
    
    public static final String ERROR_NOT_FOUND_CUSTOMER ="Customer not found";
    
    public static final String ERROR_ORDER_RESCHEDULE_SAME_DATE ="Order is already scheduled for the same date";
    
   
    

    public static final String SUCCESS_LOGIN = "Login success. Session created.";
    public static final String SUCCESS_LOGOUT = "You're logged out! Session has been terminated.";
    public static final String SUCCESS_REGISTER = "Account created.";
    public static final String SUCCESS_SESSION_INVALIDATED = "Sessions invalidated.";
    public static final String SUCCESS_FORGOT_PASSWORD_REQUEST_INIT = "An email has been sent to your address containing OTP to reset your password.";
    public static final String SUCCESS_FORGOT_PASSWORD_OTP_VERIFY = "OTP verification successful. You may now proceed to update your password.";
    public static final String SUCCESS_FORGOT_PASSWORD_RESET = "Your password has been reset successfully. Please proceed to log in.";
    public static final String SUCCESS_UPDATED_NOTICE = "Notice updated";
    public static final String SUCCESS_UPDATE_STATUS = "Status updated.";

    public static final String SUCCESS_ADD_USER = "User created.";
    public static final String SUCCESS_UPDATE_USER_DETAILS = "User details have been updated successfully.";
    public static final String SUCCESS_UPDATE_PROFILE_DETAILS = "Your profile details have been updated successfully.";
    public static final String SUCCESS_DELETE_USER = "All done! The account's been deleted.";

    public static final String SUCCESS_ADD_SCRAP_REGION = "Scrap region created.";
    public static final String SUCCESS_UPDATE_SCRAP_REGION_DETAILS = "Scrap region details have been updated successfully.";
    public static final String SUCCESS_UPDATE_SCRAP_REGION_BORDER = "Scrap region border details have been updated successfully.";
    public static final String SUCCESS_DELETE_SCRAP_REGION = "All done! The scrap region's been deleted.";
    public static final String SUCCESS_UNDELETE_SCRAP_REGION = "All done! The scrap region's been restored.";

    public static final String SUCCESS_ADD_PROVIDER_SCRAP_REGION = "Provider Scrap Region Details added successfully";
    public static final String SUCCESS_UPDATE_PROVIDER_SCRAP_REGION = "Provider Scrap Region details updated successfully";
    public static final String SUCCESS_DELETE_PROVIDER_SCRAP_REGION = "All done! The Provider Scrap Region details has been deleted.";
    public static final String SUCCESS_UNDELETE_PROVIDER_SCRAP_REGION = "All done! The Provider Scrap Region details has been restored.";

    public static final String SUCCESS_ADD_PROVIDER_TEAM = "Provider team added successfully";
    public static final String SUCCESS_DELETE_PROVIDER_TEAM = "Provider team deleted successfully";
    public static final String SUCCESS_UPDATE_PROVIDER_TEAM = "Provider team updated successfully";
    public static final String SUCCESS_UNDELETE_PROVIDER_TEAM = "Provider team restored successfully";
    public static final String SUCCESS_ADD_PROVIDERTEAM = "Provider Team added successfully";
    public static final String SUCCESS_UPDATE_PROVIDERTEAM = "Provider Team details updated successfully";
    public static final String SUCCESS_DELETE_PROVIDERTEAM = "All done! The Provider Team details has been deleted.";
    public static final String SUCCESS_UNDELETE_PROVIDERTEAM = "All done! The Provider Team details has been restored.";

    public static final String SUCCESS_ADD_COUNTRY = "Country created.";
    public static final String SUCCESS_UPDATE_COUNTRY_DETAILS = "Country details have been updated successfully.";
    public static final String SUCCESS_DELETE_COUNTRY = "All done! The country's been deleted.";
    public static final String SUCCESS_UNDELETE_COUNTRY = "All done! The country's been restored.";

    public static final String SUCCESS_ADD_CUSTOMER_ADDRESS = "Address created.";
    public static final String SUCCESS_UPDATE_CUSTOMER_ADDRESS_DETAILS = "Customer address details have been updated successfully.";
    public static final String SUCCESS_DELETE_CUSTOMER_ADDRESS = "All done! The address's been deleted.";
    public static final String SUCCESS_UNDELETE_CUSTOMER_ADDRESS = "All done! The address's been restored.";

    public static final String SUCCESS_ORDER_PLACED = "Order placed.";
    public static final String SUCCESS_ORDER_SELF_ASSIGNED = "Order assigned to you.";
    public static final String SUCCESS_UPDATE_ORDER_DETAILS = "Order details have been updated successfully.";
    public static final String SUCCESS_UPDATE_ORDER_SCHEDULE_DATE = "Order schedule date have been updated successfully.";
    public static final String SUCCESS_HEADED_TO_ORDER_LOCATION = "All done! you're departure marked successfully.";
    public static final String SUCCESS_REACHED_TO_ORDER_LOCATION = "All done! you're arrival marked successfully.";
    public static final String SUCCESS_CANCEL_ORDER = "Order has been cancelled.";
    public static final String SUCCESS_DELETE_ORDER = "All done! The order's been deleted.";
    public static final String SUCCESS_UNDELETE_ORDER = "All done! The order's been restored.";

    public static final String SUCCESS_ADD_STATE = "State added successfully";
    public static final String SUCCESS_UPDATE_STATE_DETAILS = "State details updated successfully";
    public static final String SUCCESS_DELETE_STATE = "All done! The state's been deleted.";
    public static final String SUCCESS_UNDELETE_STATE = "All done! The state's been restored.";

    public static final String SUCCESS_ADD_DISTRICT = "District added successfully";
    public static final String SUCCESS_UPDATE_DISTRICT = "District details have been updated successfully.";
    public static final String SUCCESS_DELETE_DISTRICT = "All done! The District's been deleted.";
    public static final String SUCCESS_UNDELETE_DISTRICT = "All done! The District's been restored.";

    public static final String SUCCESS_ADD_LOCAL_BODY = "LocalBody added successfully";
    public static final String SUCCESS_UPDATE_LOCAL_BODY = "LocalBody details have been updated successfully.";
    public static final String SUCCESS_UPDATE_LOCAL_BODY_BORDER = "Local body border details have been updated successfully.";
    public static final String SUCCESS_DELETE_LOCAL_BODY = "All done! The LocalBody's been deleted.";
    public static final String SUCCESS_UNDELETE_LOCAL_BODY = "All done! The LocalBody's been restored.";

    public static final String SUCCESS_ADD_LOCAL_BODY_TYPE = "LocalBodyType added successfully";
    public static final String SUCCESS_UPDATE_LOCAL_BODY_TYPE = "LocalBodyType details updated successfully";
    public static final String SUCCESS_DELETE_LOCAL_BODY_TYPE = "All done! The LocalBodyType's been deleted.";
    public static final String SUCCESS_UNDELETE_LOCAL_BODY_TYPE = "All done! The LocalBodyType's been restored.";

    public static final String SUCCESS_ADD_WARD = "Ward added successfully";
    public static final String SUCCESS_UPDATE_WARD = "Ward details have been updated successfully.";
    public static final String SUCCESS_DELETE_WARD = "All done! The Ward's been deleted.";
    public static final String SUCCESS_UNDELETE_WARD = "All done! The Ward's been restored.";

    public static final String SUCCESS_ADD_PROVIDER_WARD = "Provider Ward added successfully";
    public static final String SUCCESS_UPDATE_PROVIDER_WARD = "Provider Ward updated successfully";
    public static final String SUCCESS_DELETE_PROVIDER_WARD = "All done! The Provider Ward has been deleted.";
    public static final String SUCCESS_UNDELETE_PROVIDER_WARD = "All done! The Provider Ward has been restored.";

    public static final String SUCCESS_ADD_PROVIDER_ROLE = "Provider Role added successfully";
    public static final String SUCCESS_UPDATE_PROVIDER_ROLE = "Provider Role details have been updated successfully.";
    public static final String SUCCESS_DELETE_PROVIDER_ROLE = "All done! The Provider Role's been deleted.";
    public static final String SUCCESS_UNDELETE_PROVIDER_ROLE = "All done! The Provider Role's been restored.";

    public static final String SUCCESS_ADD_LOCALBODY = "Provider localbody added successfully";
    public static final String SUCCESS_UPDATE_LOCALBODY = "Provider localbody updated successfully";
    public static final String SUCCESS_DELETE_LOCALBODY = "All done! The Provider localbody has been deleted.";
    public static final String SUCCESS_UNDELETE_LOCALBODY = "All done! The Provider localbody has been restored.";

    public static final String SUCCESS_ADD_PROVIDER_ORDER_LIMIT = "Provider order limit added successfully";
    public static final String SUCCESS_UPDATE_PROVIDER_ORDER_LIMIT = "Provider order limit updated successfully";

    public static final String SUCCESS_ADD_PROVIDER_LOCATION_FLAGS = "Provider Service location flags added successfully";
    public static final String SUCCESS_UPDATE_PROVIDER_LOCATION_FLAGS = "Provider Service location flags details updated successfully";
    
    public static final String SUCCESS_ADD_PROVIDERTEAM_WARD = "Ward added to Provider Team successfully";
    public static final String SUCCESS_UPDATE_PROVIDERTEAM_WARD = "Provider Team Ward details updated successfully";

    public static final String SUCCESS_ADD_PROVIDER_AUTHENTICATION = "Providee Authentication added successfully";
    public static final String SUCCESS_UPDATE_PROVIDER_AUTHENTICATION = "Provider Authentication data updated successfully";
    
    public static final String SUCCESS_ADD_PICKUP_VEHICLE = "Pickup vehicle added successfully";
    public static final String SUCCESS_UPDATE_PICKUP_VEHICLE = "Pickup vehicle details updated successfully";
    public static final String SUCCESS_DELETE_PICKUP_VEHICLE = "All done! The Pickup vehile has been deleted.";
    public static final String SUCCESS_UNDELETE_PICKUP_VEHICLE = "All done! The Pickup vehicle has been restored.";
    
    public static final String SUCCESS_ADD_WASTEBAG = "Waste bag added successfully";
    public static final String SUCCESS_UPDATE_WASTEBAG = "Waste bag details updated successfully";
    
    public static final String SUCCESS_ADD_PICKUPVEHICLEDISTRICT = "Pickup Vehicle district added successfully";
    public static final String SUCCESS_UPDATE_PICKUPVEHICLEDISTRICT = "Pickup Vehicle district details updated successfully";
    
    public static final String SUCCESS_ADD_PICKUP_WEIGHT_LIMIT = "Pickup weight limit details added successfully";
    public static final String SUCCESS_UPDATE_PICKUPWEIGHT_LIMIT = "Pickup weight limit details updated successfully";
    
    public static final String SUCCESS_ADD_ASSIGNED_VEHICLE = "Assigned Vehicle added successfully";
    public static final String SUCCESS_UPDATE_ASSIGNED_VEHICLE = "Assigned Vehicle updated successfully";
    public static final String SUCCESS_DELETE_ASSIGNED_VEHICLE = "All done! Assigned Vehicle deleted successfully";
    
    public static final String SUCCESS_ADD_BWG_BAG_PRICE = "BWG bag added successfully";
    public static final String SUCCESS_UPDATE_BWG_BAG_PRICE = "BWG bag details updated successfully";
    
    public static final String SUCCESS_ADD_BWG_CLIENT = "Client added successfully";
    public static final String SUCCESS_UPDATE_BWG_CLIENT = "Client details updated successfully";
    public static final String SUCCESS_DELETE_BWG_CLIENT = "All done! Client deleted successfully.";
    public static final String SUCCESS_UNDELETE_BWG_CLIENT = "All done! Client restored successfully";
    public static final String SUCCESS_UPLOAD_BWG_CONTRACT = "BWG Contract file uploaded Successfully.";
    public static final String SUCCESS_REMOVE_BWG_CONTRACT = "BWG Contract file Removed Successfully.";

    public static final String EXECUTOR_PUSH = "pushTaskExecutor";
    public static final String EXECUTOR_MAIL = "mailTaskExecutor";
    public static final String EXECUTOR_ASYNC = "asyncTaskExecutor";

    public static final long MAX_IMAGE_FILE_SIZE = 5L * 1024L * 1024L; // 5MB
    public static final long MAX_FILE_SIZE = 10L * 1024L * 1024L; // 10MB

    public static final int MAX_DEFAULT_LENGTH = 250;
    public static final int MIN_DEFAULT_LENGTH = 2;

    public static final int MAX_10_LENGTH = 10;
    public static final int MAX_20_LENGTH = 20;
    public static final int MAX_50_LENGTH = 50;
    public static final int MAX_100_LENGTH = 100;
    public static final int MAX_150_LENGTH = 150;
    public static final int MAX_500_LENGTH = 500;
    public static final int MIN_MAX_OTP_LENGTH = 4;

    public static final int MIN_PHONE_LENGTH = 9;
    public static final int MAX_PHONE_LENGTH = 36;
}
