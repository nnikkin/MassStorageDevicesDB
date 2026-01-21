package com.nikkin.devicesdb.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.Set;

@Builder
public record ComputerDto (
    Integer id,

    @NotBlank
    @Size(max = 30)
    String name,

    Set<HardDiskDriveDto> linkedHddDtos,

    Set<SolidStateDriveDto> linkedSsdDtos,

    Set<RandomAccessMemoryDto> linkedRamDtos,

    Set<FlashDriveDto> linkedFlashDtos
) implements Identifiable {}
