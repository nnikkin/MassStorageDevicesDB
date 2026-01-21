package com.nikkin.devicesdb.Dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record HardDiskDriveDto(
        Integer id,

        @Size(max=30)
        String manufacturer,

        @Positive(message = "Объём не может быть отрицательным либо равным нулю")
        Float capacity,

        @Size(max=10)
        String driveInterface,

        @Size(max=4)
        String format,

        @Positive(message = "Энергопотребление не может быть отрицательным либо равным нулю")
        Float powerConsumption,

        Integer computerId
) implements Identifiable {}