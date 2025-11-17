package com.resustainability.recollect.dto.response;

public record ScrapTypeResponse(
        Long id,
        String name,
        String icon,
        boolean payable,
        boolean kg,
        Double price,
        Double cgst,
        Double sgst
) {}
