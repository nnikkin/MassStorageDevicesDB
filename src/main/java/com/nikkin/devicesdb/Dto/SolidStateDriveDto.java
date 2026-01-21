package com.nikkin.devicesdb.Dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SolidStateDriveDto(
        Integer id,

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
        Float powerConsumption,

        Integer computerId
) implements Identifiable {}