package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.OpticalDiscDto;
import com.nikkin.devicesdb.Entities.OpticalDisc;
import com.nikkin.devicesdb.Repos.OpticalDiscRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class OpticalDiscService {
    private final OpticalDiscRepository opticalDiscRepository;

    public OpticalDiscService(OpticalDiscRepository opticalDiscRepository) {
        this.opticalDiscRepository = opticalDiscRepository;
    }

    public OpticalDiscDto add(OpticalDiscDto dto) {
        OpticalDisc entity = mapToEntity(dto);
        OpticalDisc saved = opticalDiscRepository.save(entity);
        return mapToDto(saved);
    }

    public List<OpticalDiscDto> getAll() {
        List<OpticalDisc> allEntities = (List<OpticalDisc>) opticalDiscRepository.findAll();
        List<OpticalDiscDto> allDtos = new ArrayList<>();

        for (var entity : allEntities)
            allDtos.add(mapToDto(entity));

        return allDtos;
    }

    public Optional<OpticalDiscDto> getById(Long id) {
        return opticalDiscRepository.findById(id)
                .map(this::mapToDto);
    }

    public OpticalDiscDto delete(Long id) {
        OpticalDisc entity = opticalDiscRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        OpticalDiscDto dto = mapToDto(entity); // Маппинг в DTO
        opticalDiscRepository.deleteById(id);

        return dto;
    }

    public OpticalDiscDto update(Long id, OpticalDiscDto new_dto) {
        OpticalDisc entity = opticalDiscRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));

        entity.setName(new_dto.name());
        entity.setType(new_dto.type());
        entity.setRewriteType(new_dto.rewriteType());
        entity.setLayers(new_dto.layers());
        entity.setSpeedMultiplier(new_dto.speedMultiplier());
        entity.setCapacity(new_dto.capacity());

        OpticalDisc updated = opticalDiscRepository.save(entity);
        return mapToDto(updated);
    }

    // Мапперы
    private OpticalDiscDto mapToDto(OpticalDisc entity) {
        return new OpticalDiscDto(
                entity.getId(),
                entity.getName(),
                entity.getType(),
                entity.getCapacity(),
                entity.getSpeedMultiplier(),
                entity.getRewriteType(),
                entity.getLayers()
        );
    }

    private OpticalDisc mapToEntity(OpticalDiscDto dto) {
        OpticalDisc entity = new OpticalDisc();

        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setCapacity(dto.capacity());
        entity.setType(dto.type());
        entity.setRewriteType(dto.rewriteType());
        entity.setSpeedMultiplier(dto.speedMultiplier());
        entity.setLayers(dto.layers());

        return entity;
    }
}
