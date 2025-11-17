package com.resustainability.recollect.dto.response;

import java.util.List;

public record BioWasteCategoryResponse(
        Long id,
        String name,
        String icon,
        boolean isActive,
        List<BioWasteTypeResponse> types
) {}
