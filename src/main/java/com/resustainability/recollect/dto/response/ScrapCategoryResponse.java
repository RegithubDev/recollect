package com.resustainability.recollect.dto.response;

import java.util.List;

public record ScrapCategoryResponse(
        Long id,
        String category,
        String subcategory,
        String icon,
        List<ScrapTypeResponse> types
) {}
