package com.nikkin.devicesdb.Dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;

public record SolidStateDriveDto(
        @Id Long id,
        String name,
        String driveInterface,
        @Positive Float capacity,
        String nandType,
        @Positive Integer tbw,
        @Positive Float writeSpeed,
        @Positive Float readSpeed,
        @Positive Float powerConsumption
) {}