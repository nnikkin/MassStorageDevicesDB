package com.nikkin.devicesdb.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record HardDiskDriveDto(
        @NotBlank String name,
        @NotBlank String driveInterface,
        @Positive float capacity,
        @Positive float format,
        @Positive int rpm,
        @Positive int cache,
        @Positive float powerConsumption
) {}