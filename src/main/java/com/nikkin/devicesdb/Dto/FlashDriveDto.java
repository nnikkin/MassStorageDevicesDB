package com.nikkin.devicesdb.Dto;
import jakarta.validation.constraints.*;

public record FlashDriveDto(
        @NotBlank String name,
        @NotBlank String usbInterface,
        String usbType,
        @Positive float capacity,
        @Positive float writeSpeed,
        @Positive float readSpeed
) {}