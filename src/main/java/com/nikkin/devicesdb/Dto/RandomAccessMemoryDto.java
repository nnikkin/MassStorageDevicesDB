package com.nikkin.devicesdb.Dto;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RandomAccessMemoryDto(
        @Id
        Long id,

        @Size(max=30)
        String model,

        @Size(max=30)
        String manufacturer,

        @Size(min=1, max=10)
        String memoryType,

        @Size(min=1, max=10)
        String moduleType,

        @Positive(message = "Объём не может быть отрицательным либо равным нулю")
        Float capacity,

        @Nullable
        @Positive(message = "Тактовая частота не может быть отрицательным либо равным нулю")
        Float frequencyMhz,

        @Nullable
        @Positive(message = "CAS-латентность не может быть отрицательной либо равной нулю")
        Integer casLatency
) implements Identifiable {}