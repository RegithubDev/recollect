package com.resustainability.recollect.dto.response;

public record InvoiceResponse(
        Long id,
        String type,
        Double weight,
        Double clientPrice,
        Double subTotal,
        Double cgstAmount,
        Double sgstAmount,
        Double bagAmount,
        Double totalBill
) {}
