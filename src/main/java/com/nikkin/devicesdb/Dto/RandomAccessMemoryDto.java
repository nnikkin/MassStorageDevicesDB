package com.nikkin.devicesdb.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record RandomAccessMemoryDto(
        String model,
        String manufacturer,
        @NotBlank String type,
        @NotBlank String moduleType,
        @Positive float capacity,
        @Positive float frequencyMhz,
        @Positive float casLatency,
        boolean supportsEcc
) {}