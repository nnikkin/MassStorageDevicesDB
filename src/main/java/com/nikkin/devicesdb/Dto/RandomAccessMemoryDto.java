package com.nikkin.devicesdb.Dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RandomAccessMemoryDto(
        Integer id,

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

        Integer computerId
) implements Identifiable {}