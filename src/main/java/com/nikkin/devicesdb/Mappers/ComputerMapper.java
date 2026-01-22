package com.nikkin.devicesdb.Mappers;

import com.nikkin.devicesdb.Dto.ComputerDto;
import com.nikkin.devicesdb.Entities.Computer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RamSetMapper.class, SsdSetMapper.class, HddSetMapper.class, FlashSetMapper.class})
public interface ComputerMapper {
    @Mapping(source = "linkedHdds", target = "linkedHddDtos")
    @Mapping(source = "linkedSsds", target = "linkedSsdDtos")
    @Mapping(source = "linkedRams", target = "linkedRamDtos")
    @Mapping(source = "linkedFlashDrives", target = "linkedFlashDtos")
    ComputerDto toDto(Computer entity);
    Computer toEntity(ComputerDto dto);
}
