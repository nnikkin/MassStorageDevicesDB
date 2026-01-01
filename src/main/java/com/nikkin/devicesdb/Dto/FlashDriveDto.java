package com.nikkin.devicesdb.Dto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

public record FlashDriveDto(
        String name,
        String usbInterface,
        String usbType,
        @Positive Float capacity,
        @Positive Float writeSpeed,
        @Positive Float readSpeed
) {}