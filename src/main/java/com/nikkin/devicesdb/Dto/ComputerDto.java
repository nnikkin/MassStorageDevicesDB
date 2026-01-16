package com.nikkin.devicesdb.Dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ComputerDto (
    @Id Long id,
    @NotBlank String name,
    List<HardDiskDriveDto> linkedHddDtos,
    List<SolidStateDriveDto> linkedSsdDtos,
    List<RandomAccessMemoryDto> linkedRamDtos,
    List<FlashDriveDto> linkedFlashDtos
) implements Identifiable {}
