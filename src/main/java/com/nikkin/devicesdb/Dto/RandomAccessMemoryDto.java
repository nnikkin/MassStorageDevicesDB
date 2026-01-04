package com.nikkin.devicesdb.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record RandomAccessMemoryDto(
        @Id Long id,
        String name,
        String model,
        String manufacturer,
        @NotBlank String memoryType,
        @NotBlank String moduleType,
        @Positive Float capacity,
        @Positive Float frequencyMhz,
        @Positive Integer casLatency,
        Boolean supportsEcc
) {}