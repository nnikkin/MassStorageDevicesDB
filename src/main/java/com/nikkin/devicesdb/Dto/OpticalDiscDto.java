package com.nikkin.devicesdb.Dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;

public record OpticalDiscDto (
        @Id Long id,
        String name,
        String type,
        @Positive Float capacity,
        @Positive Integer speedMultiplier,
        String rewriteType,
        @Positive Integer layers
) {}