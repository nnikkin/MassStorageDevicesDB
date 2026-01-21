package com.nikkin.devicesdb.Mappers;

import com.nikkin.devicesdb.Dto.RandomAccessMemoryDto;
import com.nikkin.devicesdb.Entities.RandomAccessMemory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RamMapper {
    RandomAccessMemoryDto toDto(RandomAccessMemory entity);
    RandomAccessMemory toEntity(RandomAccessMemoryDto dto);
}
