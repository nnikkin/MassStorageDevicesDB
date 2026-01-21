package com.nikkin.devicesdb.Mappers;

import com.nikkin.devicesdb.Dto.SolidStateDriveDto;
import com.nikkin.devicesdb.Entities.SolidStateDrive;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SsdMapper {
    SolidStateDriveDto toDto(SolidStateDrive entity);
    SolidStateDrive toEntity(SolidStateDriveDto dto);
}
