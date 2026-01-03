package com.nikkin.devicesdb.Dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;

public record FloppyDiskDto(
        @Id Long id,
        String name,
        @Positive Float capacity,
        @Positive Float format,
        String diskDensity,
        Boolean isDoubleSided
) {}