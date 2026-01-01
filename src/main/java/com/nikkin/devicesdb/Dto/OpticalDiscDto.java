package com.nikkin.devicesdb.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record OpticalDiscDto(
        @NotBlank String name,
        @NotBlank String type,
        @Positive Float capacity,
        @Positive Integer speedMultiplier,
        String rewriteType,
        @Positive Integer layers
) {}