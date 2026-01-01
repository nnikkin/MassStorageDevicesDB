package com.nikkin.devicesdb.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record FloppyDiskDto(
        String name,
        @Positive Float capacity,
        @Positive Float format,
        String diskDensity
) {}