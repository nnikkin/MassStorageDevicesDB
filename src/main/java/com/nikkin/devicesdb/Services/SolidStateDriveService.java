package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.SolidStateDriveDto;
import com.nikkin.devicesdb.Entities.SolidStateDrive;
import com.nikkin.devicesdb.Repos.SolidStateDriveRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SolidStateDriveService {
    private final SolidStateDriveRepository solidStateDriveRepository;

    public SolidStateDriveService(SolidStateDriveRepository solidStateDriveRepository) {
        this.solidStateDriveRepository = solidStateDriveRepository;
    }

    public SolidStateDriveDto add(SolidStateDriveDto dto) {
        SolidStateDrive entity = mapToEntity(dto);
        SolidStateDrive saved = solidStateDriveRepository.save(entity);
        return mapToDto(saved);
    }

    public List<SolidStateDriveDto> getAll() {
        List<SolidStateDrive> allEntities = (List<SolidStateDrive>) solidStateDriveRepository.findAll();
        List<SolidStateDriveDto> allDtos = new ArrayList<>();

        for (var entity : allEntities)
            allDtos.add(mapToDto(entity));

        return allDtos;
    }

    public Optional<SolidStateDriveDto> getById(Long id) {
        return solidStateDriveRepository.findById(id)
                .map(this::mapToDto);
    }

    public SolidStateDriveDto delete(Long id) {
        SolidStateDrive entity = solidStateDriveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        SolidStateDriveDto dto = mapToDto(entity); // Маппинг в DTO
        solidStateDriveRepository.deleteById(id);

        return dto;
    }

    public SolidStateDriveDto update(Long id, SolidStateDriveDto new_dto) {
        SolidStateDrive old_entity = solidStateDriveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));
        SolidStateDrive new_entity = mapToEntity(new_dto);
        solidStateDriveRepository.save(new_entity);

        return new_dto;
    }

    // Мапперы
    private SolidStateDriveDto mapToDto(SolidStateDrive entity) {
        /*
            public SolidStateDriveDto(
                String name,
                @NotBlank String driveInterface,
                @Positive Float capacity,
                String nandType,
                @Positive Integer tbw,
                @Positive Float writeSpeed,
                @Positive Float readSpeed,
                @Positive Float powerConsumption
            )
        */

        return new SolidStateDriveDto(
            entity.getName(),
            entity.getDriveInterface(),
            entity.getCapacity(),
            entity.getNandType(),
            entity.getTbw(),
            entity.getWriteSpeed(),
            entity.getReadSpeed(),
            entity.getPowerConsumption()
        );
    }

    private SolidStateDrive mapToEntity(SolidStateDriveDto dto) {
        SolidStateDrive entity = new SolidStateDrive();

        entity.setName(entity.getName());
        entity.setDriveInterface(entity.getDriveInterface());
        entity.setCapacity(entity.getCapacity());
        entity.setNandType(entity.getNandType());
        entity.setTbw(entity.getTbw());
        entity.setWriteSpeed(entity.getWriteSpeed());
        entity.setReadSpeed(entity.getReadSpeed());
        entity.setPowerConsumption(entity.getPowerConsumption());

        return entity;
    }
}
