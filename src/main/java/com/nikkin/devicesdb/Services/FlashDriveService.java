package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.FlashDriveDto;
import com.nikkin.devicesdb.Entities.FlashDrive;
import com.nikkin.devicesdb.Mappers.FlashMapper;
import com.nikkin.devicesdb.Repos.FlashDriveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class FlashDriveService implements IService<FlashDriveDto> {
    @Autowired
    private FlashDriveRepository repo;

    @Autowired
    private FlashMapper mapper;

    @Override
    public FlashDriveDto add(FlashDriveDto dto) {
        // Создаём объект из DTO
        FlashDrive newFlashDrive = toEntity(dto);

        // Сохраняем объект
        FlashDrive savedFlashDrive = repo.save(newFlashDrive);

        // Создаём FlashDriveDto из полученного объекта
        FlashDriveDto restoredDto = toDto(savedFlashDrive);
        return restoredDto;
    }

    @Override
    public FlashDriveDto getById(Integer id) {
        // получаем FlashDrive из БД
        FlashDrive computer = repo.getReferenceById(id);

        // получаем FlashDriveDto
        return toDto(computer);
    }

    @Override
    public List<FlashDriveDto> getAll() {
        return repo.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public FlashDriveDto delete(Integer id) {
        // получаем FlashDrive из БД
        FlashDrive computer = repo.getReferenceById(id);

        // Удаляем объект
        FlashDrive deletedFlashDrive = repo.save(computer);

        // получаем FlashDriveDto
        return toDto(deletedFlashDrive);
    }

    @Override
    public FlashDriveDto update(Integer id, FlashDriveDto new_dto) {
        // получаем FlashDrive из БД
        FlashDrive flashDrive = repo.getReferenceById(id);

        // изменяем значения полей FlashDrive на новые
        var tmp = mapper.toEntity(new_dto);
        flashDrive.setName(tmp.getName());
        flashDrive.setCapacity(tmp.getCapacity());
        flashDrive.setUsbInterface(tmp.getUsbInterface());
        flashDrive.setReadSpeed(tmp.getReadSpeed());
        flashDrive.setWriteSpeed(tmp.getWriteSpeed());
        flashDrive.setComputer(tmp.getComputer());
        FlashDrive updatedFlashDrive = repo.save(flashDrive);

        // Создаём FlashDriveDto из полученного объекта
        return toDto(updatedFlashDrive);
    }

    @Override
    public int getItemsCount() {
        return Math.toIntExact(repo.findAll().size());
    }

    private FlashDrive toEntity(FlashDriveDto dto) {
        return mapper.toEntity(dto);
    }

    private FlashDriveDto toDto(FlashDrive entity) {
        return mapper.toDto(entity);
    }
}
