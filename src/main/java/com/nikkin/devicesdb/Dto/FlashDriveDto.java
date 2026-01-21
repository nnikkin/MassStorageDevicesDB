package com.nikkin.devicesdb.Dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record FlashDriveDto(
        Integer id,

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

        Integer computerId
)implements Identifiable {}