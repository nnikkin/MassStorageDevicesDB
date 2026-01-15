package com.nikkin.devicesdb.Dto;

import jakarta.annotation.Nullable;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record OpticalDiscDto (
        @Id
        Long id,

        @Size(max=30)
        String name,

        @Size(max=10)
        String type,

        @Positive(message = "Объём не может быть отрицательным либо равным нулю")
        Float capacity,

        @Nullable
        @Positive(message = "Множитель скорости не может быть отрицательным либо равным нулю")
        Integer speedMultiplier,

        @Size(max=4)
        String rewriteType,

        @Nullable
        @Positive(message = "Количество слоёв не может быть отрицательным либо равным нулю")
        Integer layers
) implements Identifiable {}