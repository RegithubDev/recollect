package com.resustainability.recollect.dto.payload;

import java.time.LocalDate;

public record PayloadLocalBodyAvailability(
        Long id,
        LocalDate date,
        Integer limit
) {}
