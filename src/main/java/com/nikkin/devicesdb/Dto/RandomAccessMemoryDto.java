package com.nikkin.devicesdb.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record RandomAccessMemoryDto(
        String name,
        String model,
        String manufacturer,
        @NotBlank String type,
        @NotBlank String moduleType,
        @Positive Float capacity,
        @Positive Float frequencyMhz,
        @Positive Float casLatency,
        Boolean supportsEcc
) {}