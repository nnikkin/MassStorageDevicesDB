package com.nikkin.devicesdb.Dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record HardDiskDriveDto(
        @Id
        Long id,

        @Size(max=30)
        String name,

        @Positive(message = "Объём не может быть отрицательным либо равным нулю")
        Float capacity,

        @Size(max=10)
        String driveInterface,

        @Size(max=4)
        String format,

        @Positive(message = "RPM не может быть отрицательным либо равным нулю")
        Integer rpm,

        @Positive(message = "Объём кэша не может быть отрицательным либо равным нулю")
        Integer cache,

        @Positive(message = "Энергопотребление не может быть отрицательным либо равным нулю")
        Float powerConsumption
) implements Identifiable {}