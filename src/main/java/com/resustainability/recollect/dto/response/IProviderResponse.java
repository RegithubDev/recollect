package com.resustainability.recollect.dto.response;

public interface IProviderResponse {
    Long getId();
    String getCode();
    String getFullName();
    String getPhoneNumber();

    Double getScrapCashInHand();
    Double getBioCashInHand();
    Double getBwgScrapCashInHand();
    Double getBwgBioCashInHand();
    Double getTotalCashInHand();

    String getPassword();

    Boolean getScrapPickup();
    Boolean getBiowastePickup();
    Boolean getBwgBioPickup();
    Boolean getBwgScrapPickup();
    Integer getOrderPickupLimit();

    Boolean getIsActive();
    Boolean getIsDeleted();

    Long getStateId();
    String getStateName();
    String getStateCode();
}
