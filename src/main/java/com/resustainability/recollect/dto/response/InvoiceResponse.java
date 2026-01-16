package com.resustainability.recollect.dto.response;

import java.time.LocalDateTime;

public record InvoiceResponse(
        Long id,
        String type,
        Double weight,
        Double clientPrice,
        Double subTotal,
        Double cgstAmount,
        Double sgstAmount,
        Double walletDeduct,
        Double bagAmount,
        Double serviceCharge,
        Double totalBill,
        LocalDateTime billedAt
) {}
