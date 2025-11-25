package com.resustainability.recollect.dto.payload;

import java.time.LocalDate;

public record PayloadScrapRegionAvailability(
        Long id,
        LocalDate date,
        Integer limit
) {}
