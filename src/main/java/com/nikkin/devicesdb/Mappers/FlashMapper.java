package com.nikkin.devicesdb.Mappers;

import com.nikkin.devicesdb.Dto.FlashDriveDto;
import com.nikkin.devicesdb.Entities.Computer;
import com.nikkin.devicesdb.Entities.FlashDrive;
import com.nikkin.devicesdb.Repos.ComputerRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class FlashMapper {

    @Autowired
    protected ComputerRepository computerRepository;

    @Mapping(source = "computer.id", target = "computerId")
    public abstract FlashDriveDto toDto(FlashDrive entity);

    @Mapping(source = "computerId", target = "computer", qualifiedByName = "idToComputer")
    public abstract FlashDrive toEntity(FlashDriveDto dto);

    @Named("idToComputer")
    protected Computer idToComputer(Integer computerId) {
        if (computerId == null) {
            return null;
        }
        return computerRepository.findById(computerId).orElse(null);
    }
}
