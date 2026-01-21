package com.nikkin.devicesdb.Mappers;

import com.nikkin.devicesdb.Dto.SolidStateDriveDto;
import com.nikkin.devicesdb.Entities.SolidStateDrive;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring", uses = SsdMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SsdSetMapper {
    Set<SolidStateDriveDto> toDto(Set<SolidStateDrive> entitiesList);
    Set<SolidStateDrive> toEntity(Set<SolidStateDriveDto> dtoList);
}
