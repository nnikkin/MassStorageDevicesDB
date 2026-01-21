package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.HardDiskDriveDto;
import com.nikkin.devicesdb.Entities.HardDiskDrive;
import com.nikkin.devicesdb.Mappers.HddMapper;
import com.nikkin.devicesdb.Repos.HddRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class HddService implements IService<HardDiskDriveDto> {
    @Autowired
    private HddRepository repo;

    @Autowired
    private HddMapper mapper;

    @Override
    public HardDiskDriveDto add(HardDiskDriveDto dto) {
        // Создаём объект из DTO
        HardDiskDrive newHardDiskDrive = toEntity(dto);

        // Сохраняем объект
        HardDiskDrive savedHardDiskDrive = repo.save(newHardDiskDrive);

        // Создаём HardDiskDriveDto из полученного объекта
        HardDiskDriveDto restoredDto = toDto(savedHardDiskDrive);
        return restoredDto;
    }

    @Override
    public HardDiskDriveDto getById(Integer id) {
        // получаем HardDiskDrive из БД
        HardDiskDrive computer = repo.getReferenceById(id);

        // получаем HardDiskDriveDto
        return toDto(computer);
    }

    @Override
    public List<HardDiskDriveDto> getAll() {
        return repo.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public HardDiskDriveDto delete(Integer id) {
        // получаем HardDiskDrive из БД
        HardDiskDrive computer = repo.getReferenceById(id);

        // Удаляем объект
        HardDiskDrive deletedHardDiskDrive = repo.save(computer);

        // получаем HardDiskDriveDto
        return toDto(deletedHardDiskDrive);
    }

    @Override
    public HardDiskDriveDto update(Integer id, HardDiskDriveDto new_dto) {
        // получаем HardDiskDrive из БД
        HardDiskDrive hardDiskDrive = repo.getReferenceById(id);

        // изменяем значения полей HardDiskDrive на новые
        var tmp = mapper.toEntity(new_dto);
        hardDiskDrive.setManufacturer(tmp.getManufacturer());
        hardDiskDrive.setCapacity(tmp.getCapacity());
        hardDiskDrive.setDriveInterface(tmp.getDriveInterface());
        hardDiskDrive.setFormat(tmp.getFormat());
        hardDiskDrive.setPowerConsumption(tmp.getPowerConsumption());
        hardDiskDrive.setComputer(tmp.getComputer());
        HardDiskDrive updatedHardDiskDrive = repo.save(hardDiskDrive);

        // Создаём HardDiskDriveDto из полученного объекта
        return toDto(updatedHardDiskDrive);
    }

    @Override
    public int getItemsCount() {
        return Math.toIntExact(repo.findAll().size());
    }

    private HardDiskDrive toEntity(HardDiskDriveDto dto) {
        return mapper.toEntity(dto);
    }

    private HardDiskDriveDto toDto(HardDiskDrive entity) {
        return mapper.toDto(entity);
    }
}
