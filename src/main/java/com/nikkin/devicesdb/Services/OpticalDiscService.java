package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.FlashDriveDto;
import com.nikkin.devicesdb.Dto.OpticalDiscDto;
import com.nikkin.devicesdb.Entities.FlashDrive;
import com.nikkin.devicesdb.Entities.OpticalDisc;
import com.nikkin.devicesdb.Repos.FlashDriveRepository;
import com.nikkin.devicesdb.Repos.OpticalDiscRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class OpticalDiscService extends BaseService<OpticalDisc, OpticalDiscDto> {
    public OpticalDiscService(OpticalDiscRepository repository) {
        super();
        setCrudRepository(repository);
    }

    @Override
    protected OpticalDiscDto mapToDto(OpticalDisc entity) {
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

    @Override
    protected void updateEntity(OpticalDisc entity, OpticalDiscDto dto) {
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setCapacity(dto.capacity());
        entity.setType(dto.type());
        entity.setRewriteType(dto.rewriteType());
        entity.setSpeedMultiplier(dto.speedMultiplier());
        entity.setLayers(dto.layers());
    }

    @Override
    protected OpticalDisc mapToEntity(OpticalDiscDto dto) {
        OpticalDisc entity = new OpticalDisc();
        updateEntity(entity, dto);
        return entity;
    }
}
