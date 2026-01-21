package com.nikkin.devicesdb.Mappers;

import com.nikkin.devicesdb.Dto.ComputerDto;
import com.nikkin.devicesdb.Entities.Computer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RamSetMapper.class, SsdSetMapper.class, HddSetMapper.class, FlashSetMapper.class})
public interface ComputerMapper {
    ComputerDto toDto(Computer entity);
    Computer toEntity(ComputerDto dto);
}
