package com.nikkin.devicesdb.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record HardDiskDriveDto(
        @NotBlank String name,
        @Positive Float capacity,
        @NotBlank String driveInterface,
        @Positive Float format,
        @Positive Integer rpm,
        @Positive Integer cache,
        @Positive Float powerConsumption
) {}