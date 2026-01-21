package com.nikkin.devicesdb.Mappers;

import com.nikkin.devicesdb.Dto.FlashDriveDto;
import com.nikkin.devicesdb.Entities.FlashDrive;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring", uses = FlashMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FlashSetMapper {
    Set<FlashDriveDto> toDto(Set<FlashDrive> entitiesList);
    Set<FlashDrive> toEntity(Set<FlashDriveDto> dtoList);
}
