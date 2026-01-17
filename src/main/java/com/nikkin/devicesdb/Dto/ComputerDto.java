package com.nikkin.devicesdb.Dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ComputerDto (
    @Id Long id,
    @NotBlank @Size(max = 30) String name,
    List<HardDiskDriveDto> linkedHddDtos,
    List<SolidStateDriveDto> linkedSsdDtos,
    List<RandomAccessMemoryDto> linkedRamDtos,
    List<FlashDriveDto> linkedFlashDtos
) implements Identifiable {}
