package com.nikkin.devicesdb.Mappers;

import com.nikkin.devicesdb.Dto.HardDiskDriveDto;
import com.nikkin.devicesdb.Entities.HardDiskDrive;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring", uses = HddMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface HddSetMapper {
    Set<HardDiskDriveDto> toDto(Set<HardDiskDrive> entitiesList);
    Set<HardDiskDrive> toEntity(Set<HardDiskDriveDto> dtoList);
}
