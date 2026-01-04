package com.nikkin.devicesdb.Dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;

public record HardDiskDriveDto(
        @Id Long id,
        String name,
        @Positive Float capacity,
        String driveInterface,
        String format,
        @Positive Integer rpm,
        @Positive Integer cache,
        @Positive Float powerConsumption
) {}