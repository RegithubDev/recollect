package com.resustainability.recollect.dto.request;

import java.time.LocalDate;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdateBwgClientRequest(
        Long id,
        String fullName,
        String email,
        String phoneNumber,
        String alternateNumber,

        Long stateId,
        Long districtId,
        Long wardId,
        Long scrapRegionId,

        String serviceType,
        String clientCategory,

        Integer familyNumber,
        String verificationStatus,

        Boolean monthlyContract,
        Double clientPrice,
        Double clientCgst,
        Double clientSgst,

        String gstName,
        String gstNo,
        String accountNumber,
        String ifscCode,

        String username,
        String password,

        String remark,
        String address,

        LocalDate contractStartDate,
        LocalDate contractEndDate,

        String collectionFrequency,
        Boolean isActive

) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(id);

        ValidationUtils.validateName(fullName);
        ValidationUtils.validatePhone(phoneNumber);
        ValidationUtils.validatePassword(password);
        ValidationUtils.validateAddressId(districtId);

       
        if (alternateNumber != null) {
            ValidationUtils.validateAltPhone(alternateNumber);
        }

        if (email != null) {
            ValidationUtils.validateEmail(email);
        }

        ValidationUtils.validateId(stateId);
        ValidationUtils.validateDistrictId(districtId);

        ValidationUtils.validateNonNegative(familyNumber, "Family Number");

        ValidationUtils.validateNonNegative(clientPrice, "Client Price");
        ValidationUtils.validateNonNegative(clientCgst, "Client CGST");
        ValidationUtils.validateNonNegative(clientSgst, "Client SGST");

        ValidationUtils.validateRequired(collectionFrequency, "Collection Frequency");
        ValidationUtils.validateRequired(address, "Address");

       
        if (Boolean.TRUE.equals(monthlyContract)) {
            ValidationUtils.validateRange(contractStartDate, contractEndDate);
        }
    
    }  
}
