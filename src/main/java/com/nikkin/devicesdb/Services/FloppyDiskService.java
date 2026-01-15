package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.FloppyDiskDto;
import com.nikkin.devicesdb.Entities.FloppyDisk;
import com.nikkin.devicesdb.Repos.FloppyDiskRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class FloppyDiskService extends BaseService<FloppyDisk, FloppyDiskDto> {
    public FloppyDiskService(FloppyDiskRepository repository) {
        super();
        setCrudRepository(repository);
    }

    @Override
    protected FloppyDiskDto mapToDto(FloppyDisk entity) {
        return new FloppyDiskDto(
            entity.getId(),
            entity.getName(),
            entity.getCapacity(),
            entity.getFormat(),
            entity.getDiskDensity(),
            entity.isDoubleSided()
        );
    }

    @Override
    protected void updateEntity(FloppyDisk entity, FloppyDiskDto dto) {
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setCapacity(dto.capacity());
        entity.setFormat(dto.format());
        entity.setDiskDensity(dto.diskDensity());
        entity.setDoubleSided(dto.isDoubleSided());
    }

    @Override
    protected FloppyDisk mapToEntity(FloppyDiskDto dto) {
        FloppyDisk entity = new FloppyDisk();
        updateEntity(entity, dto);
        return entity;
    }
}
