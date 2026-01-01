package com.nikkin.devicesdb.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record SolidStateDriveDto(
        String name,
        @NotBlank String driveInterface,
        @Positive Float capacity,
        String nandType,
        @Positive Integer tbw,
        @Positive Float writeSpeed,
        @Positive Float readSpeed,
        @Positive Float powerConsumption
) {}