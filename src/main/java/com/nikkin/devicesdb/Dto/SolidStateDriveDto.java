package com.nikkin.devicesdb.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record SolidStateDriveDto(
        String name,
        @NotBlank String driveInterface,
        @Positive float capacity,
        String nandType,
        @Positive int tbwTb,
        @Positive float writeSpeed,
        @Positive float readSpeed,
        @Positive float powerConsumption
) {}