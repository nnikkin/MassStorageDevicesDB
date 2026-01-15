package com.nikkin.devicesdb.Dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record FloppyDiskDto(
        @Id
        Long id,

        @Size(max=30)
        String name,

        @Positive(message = "Объём не может быть отрицательным либо равным нулю")
        Float capacity,

        @Positive(message = "Размер дискеты не может быть отрицательным либо равным нулю")
        Float format,

        @Size(max=2)
        String diskDensity,

        Boolean isDoubleSided
) implements Identifiable {}