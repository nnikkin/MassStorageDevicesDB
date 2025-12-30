package com.nikkin.devicesdb.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record FloppyDiskDto(
        String name,
        @Positive float capacity,
        @Positive float format,
        char diskDensity
) {}