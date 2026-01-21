package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.SolidStateDriveDto;
import com.nikkin.devicesdb.Entities.SolidStateDrive;
import com.nikkin.devicesdb.Mappers.SsdMapper;
import com.nikkin.devicesdb.Repos.SsdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class SsdService implements IService<SolidStateDriveDto> {
    @Autowired
    private SsdRepository repo;

    @Autowired
    private SsdMapper mapper;

    @Override
    public SolidStateDriveDto add(SolidStateDriveDto dto) {
        // Создаём объект из DTO
        SolidStateDrive newSolidStateDrive = toEntity(dto);

        // Сохраняем объект
        SolidStateDrive savedSolidStateDrive = repo.save(newSolidStateDrive);

        // Создаём SolidStateDriveDto из полученного объекта
        SolidStateDriveDto restoredDto = toDto(savedSolidStateDrive);
        return restoredDto;
    }

    @Override
    public SolidStateDriveDto getById(Integer id) {
        // получаем SolidStateDrive из БД
        SolidStateDrive computer = repo.getReferenceById(id);

        // получаем SolidStateDriveDto
        return toDto(computer);
    }

    @Override
    public List<SolidStateDriveDto> getAll() {
        return repo.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public SolidStateDriveDto delete(Integer id) {
        // получаем SolidStateDrive из БД
        SolidStateDrive computer = repo.getReferenceById(id);

        // Удаляем объект
        SolidStateDrive deletedSolidStateDrive = repo.save(computer);

        // получаем SolidStateDriveDto
        return toDto(deletedSolidStateDrive);
    }

    @Override
    public SolidStateDriveDto update(Integer id, SolidStateDriveDto new_dto) {
        // получаем SolidStateDrive из БД
        SolidStateDrive solidStateDrive = repo.getReferenceById(id);

        // изменяем значения полей SolidStateDrive на новые
        var tmp = mapper.toEntity(new_dto);
        solidStateDrive.setManufacturer(tmp.getManufacturer());
        solidStateDrive.setCapacity(tmp.getCapacity());
        solidStateDrive.setDriveInterface(tmp.getDriveInterface());
        solidStateDrive.setNandType(tmp.getNandType());
        solidStateDrive.setReadSpeed(tmp.getReadSpeed());
        solidStateDrive.setWriteSpeed(tmp.getWriteSpeed());
        solidStateDrive.setPowerConsumption(tmp.getPowerConsumption());
        solidStateDrive.setComputer(tmp.getComputer());
        SolidStateDrive updatedSolidStateDrive = repo.save(solidStateDrive);

        // Создаём SolidStateDriveDto из полученного объекта
        return toDto(updatedSolidStateDrive);
    }

    @Override
    public int getItemsCount() {
        return Math.toIntExact(repo.findAll().size());
    }

    private SolidStateDrive toEntity(SolidStateDriveDto dto) {
        return mapper.toEntity(dto);
    }

    private SolidStateDriveDto toDto(SolidStateDrive entity) {
        return mapper.toDto(entity);
    }
}
