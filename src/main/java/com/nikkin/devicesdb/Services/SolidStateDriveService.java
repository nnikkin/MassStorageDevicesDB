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

    public SolidStateDriveDto update(Long id, SolidStateDriveDto dto) {
        SolidStateDrive entity = solidStateDriveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setDriveInterface(dto.driveInterface());
        entity.setCapacity(dto.capacity());
        entity.setNandType(dto.nandType());
        entity.setTbw(dto.tbw());
        entity.setWriteSpeed(dto.writeSpeed());
        entity.setReadSpeed(dto.readSpeed());
        entity.setPowerConsumption(dto.powerConsumption());

        SolidStateDrive updated = solidStateDriveRepository.save(entity);
        return mapToDto(updated);
    }

    // Мапперы
    private SolidStateDriveDto mapToDto(SolidStateDrive entity) {
        return new SolidStateDriveDto(
            entity.getId(),
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

        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setDriveInterface(dto.driveInterface());
        entity.setCapacity(dto.capacity());
        entity.setNandType(dto.nandType());
        entity.setTbw(dto.tbw());
        entity.setWriteSpeed(dto.writeSpeed());
        entity.setReadSpeed(dto.readSpeed());
        entity.setPowerConsumption(dto.powerConsumption());

        return entity;
    }
}
