package com.resustainability.recollect.dto.response;

public record BioWasteTypeResponse(
        Long id,
        String name,
        String icon,
        boolean isActive
) {}
