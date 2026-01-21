package com.nikkin.devicesdb.Mappers;

import com.nikkin.devicesdb.Dto.HardDiskDriveDto;
import com.nikkin.devicesdb.Entities.HardDiskDrive;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HddMapper {
    HardDiskDriveDto toDto(HardDiskDrive entity);
    HardDiskDrive toEntity(HardDiskDriveDto dto);
}
