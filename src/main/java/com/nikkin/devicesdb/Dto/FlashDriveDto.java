package com.nikkin.devicesdb.Dto;
import jakarta.annotation.Nullable;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;

public record FlashDriveDto(
        @Id
        Long id,

        @Size(max = 30)
        String name,

        @Size(max = 10)
        String usbInterface,

        @Positive(message = "Объём не может быть отрицательным либо равным нулю")
        Float capacity,

        @Positive(message = "Скорость записи не может быть отрицательной либо равной нулю")
        Float writeSpeed,

        @Positive(message = "Скорость чтения не может быть отрицательной либо равной нулю")
        Float readSpeed,

        Long computerId
) implements Identifiable {}