package com.nikkin.devicesdb.Dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record SolidStateDriveDto(
        @Id Long id,

        @Size(max=30)
        String manufacturer,

        @Size(max=10)
        String driveInterface,

        @Positive(message = "Объём не может быть отрицательным либо равным нулю")
        Float capacity,

        @Size(max=10)
        String nandType,

        @Positive(message = "Скорость чтения не может быть отрицательным либо равным нулю")
        Float writeSpeed,

        @Positive(message = "Скорость записи не может быть отрицательным либо равным нулю")
        Float readSpeed,

        @Positive(message = "Значение энергопотребления не может быть отрицательным либо равным нулю")
        Float powerConsumption
) implements Identifiable {}