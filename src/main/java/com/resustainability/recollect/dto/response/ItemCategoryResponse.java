package com.resustainability.recollect.dto.response;

import java.util.List;

public record ItemCategoryResponse(
        Long id,
        String category,
        String subcategory,
        String icon,
        List<ItemCategoryTypeResponse> types
) {}
