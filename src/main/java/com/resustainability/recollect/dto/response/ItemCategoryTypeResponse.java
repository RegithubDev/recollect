package com.resustainability.recollect.dto.response;

public record ItemCategoryTypeResponse(
        Long id,
        String name,
        String icon,
        boolean payable,
        boolean kg,
        Double price,
        Double cgst,
        Double sgst
) {
    public ItemCategoryTypeResponse(Long id, String name) {
        this(id, name, null, false, false, null, null, null);
    }
}