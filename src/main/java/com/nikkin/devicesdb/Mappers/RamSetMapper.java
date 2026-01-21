package com.nikkin.devicesdb.Mappers;

import com.nikkin.devicesdb.Dto.RandomAccessMemoryDto;
import com.nikkin.devicesdb.Entities.RandomAccessMemory;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring", uses = RamMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RamSetMapper {
    Set<RandomAccessMemoryDto> toDto(Set<RandomAccessMemory> entitiesList);
    Set<RandomAccessMemory> toEntity(Set<RandomAccessMemoryDto> dtoList);
}
