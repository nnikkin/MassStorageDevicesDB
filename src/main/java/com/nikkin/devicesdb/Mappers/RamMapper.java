package com.nikkin.devicesdb.Mappers;

import com.nikkin.devicesdb.Dto.RandomAccessMemoryDto;
import com.nikkin.devicesdb.Dto.RandomAccessMemoryDto;
import com.nikkin.devicesdb.Entities.Computer;
import com.nikkin.devicesdb.Entities.RandomAccessMemory;
import com.nikkin.devicesdb.Entities.RandomAccessMemory;
import com.nikkin.devicesdb.Repos.ComputerRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class RamMapper {

    @Autowired
    protected ComputerRepository computerRepository;

    @Mapping(source = "computer.id", target = "computerId")
    public abstract RandomAccessMemoryDto toDto(RandomAccessMemory entity);

    @Mapping(source = "computerId", target = "computer", qualifiedByName = "idToComputer")
    public abstract RandomAccessMemory toEntity(RandomAccessMemoryDto dto);

    @Named("idToComputer")
    protected Computer idToComputer(Integer computerId) {
        if (computerId == null) {
            return null;
        }
        return computerRepository.findById(computerId).orElse(null);
    }
}