package com.nikkin.devicesdb.Dto;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;

public record FlashDriveDto(
        @Id Long id,
        String name,
        String usbInterface,
        String usbType,
        @Positive Float capacity,
        @Positive Float writeSpeed,
        @Positive Float readSpeed
) {}