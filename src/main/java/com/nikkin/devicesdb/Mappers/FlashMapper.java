package com.nikkin.devicesdb.Mappers;

import com.nikkin.devicesdb.Dto.FlashDriveDto;
import com.nikkin.devicesdb.Entities.FlashDrive;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlashMapper {
    FlashDriveDto toDto(FlashDrive entity);
    FlashDrive toEntity(FlashDriveDto dto);
}
