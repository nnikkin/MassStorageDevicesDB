package com.nikkin.devicesdb.Services;

import com.nikkin.devicesdb.Dto.*;
import com.nikkin.devicesdb.Entities.*;
import com.nikkin.devicesdb.Repos.ComputerRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ComputerService extends BaseService<Computer, ComputerDto> {
    public ComputerService(ComputerRepository repository) {
        super();
        setRepository(repository);
    }

    @Override
    protected ComputerDto mapToDto(Computer entity) {
        return new ComputerDto(
                entity.getId(),
                entity.getName(),
                entity.getLinkedHdds()
                    .stream()
                    .map(drive -> new HardDiskDriveDto(
                        drive.getId(),
                        drive.getManufacturer(),
                        drive.getCapacity(),
                        drive.getDriveInterface(),
                        drive.getFormat(),
                        drive.getPowerConsumption(),
                        entity.getId())
                    )
                    .toList(),
                entity.getLinkedSsds()
                    .stream()
                    .map(drive -> new SolidStateDriveDto(
                        drive.getId(),
                        drive.getManufacturer(),
                        drive.getDriveInterface(),
                        drive.getCapacity(),
                        drive.getNandType(),
                        drive.getWriteSpeed(),
                        drive.getReadSpeed(),
                        drive.getPowerConsumption(),
                        entity.getId())
                    )
                    .toList(),
                entity.getLinkedRams()
                    .stream()
                    .map(drive -> new RandomAccessMemoryDto(
                        drive.getId(),
                        drive.getModel(),
                        drive.getManufacturer(),
                        drive.getMemoryType(),
                        drive.getModuleType(),
                        drive.getCapacity(),
                        drive.getFrequencyMhz(),
                        entity.getId())
                    )
                    .toList(),
                entity.getLinkedFlashDrives()
                    .stream()
                    .map(drive -> new FlashDriveDto(
                        drive.getId(),
                        drive.getName(),
                        drive.getUsbInterface(),
                        drive.getCapacity(),
                        drive.getWriteSpeed(),
                        drive.getReadSpeed(),
                        entity.getId())
                    )
                    .toList()
        );
    }

    @Override
    protected void updateEntity(Computer entity, ComputerDto dto) {
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setLinkedHdds(
                dto.linkedHddDtos()
                    .stream()
                    .map(item -> {
                        var hdd = new HardDiskDrive();
                        hdd.setId(item.id());
                        hdd.setManufacturer(item.manufacturer());
                        hdd.setDriveInterface(item.driveInterface());
                        hdd.setCapacity(item.capacity());
                        hdd.setFormat(item.format());
                        hdd.setPowerConsumption(item.powerConsumption());
                        hdd.setComputer(entity);
                        return hdd;
                    })
                    .toList()
        );
        entity.setLinkedRams(
                dto.linkedRamDtos()
                    .stream()
                    .map(item -> {
                        var ram = new RandomAccessMemory();
                        ram.setId(item.id());
                        ram.setManufacturer(item.manufacturer());
                        ram.setMemoryType(item.memoryType());
                        ram.setModuleType(item.moduleType());
                        ram.setCapacity(item.capacity());
                        ram.setFrequencyMhz(item.frequencyMhz());
                        ram.setComputer(entity);
                        return ram;
                    })
                    .toList()
        );
        entity.setLinkedSsds(
                dto.linkedSsdDtos()
                    .stream()
                    .map(item -> {
                        var ssd = new SolidStateDrive();
                        ssd.setId(item.id());
                        ssd.setManufacturer(item.manufacturer());
                        ssd.setDriveInterface(item.driveInterface());
                        ssd.setCapacity(item.capacity());
                        ssd.setNandType(item.nandType());
                        ssd.setReadSpeed(item.readSpeed());
                        ssd.setWriteSpeed(item.writeSpeed());
                        ssd.setPowerConsumption(item.powerConsumption());
                        ssd.setComputer(entity);
                        return ssd;
                    })
                    .toList()
        );
        entity.setLinkedFlashDrives(
                dto.linkedFlashDtos()
                    .stream()
                    .map(item -> {
                        var flash = new FlashDrive();
                        flash.setId(item.id());
                        flash.setName(item.name());
                        flash.setUsbInterface(item.usbInterface());
                        flash.setCapacity(item.capacity());
                        flash.setReadSpeed(item.readSpeed());
                        flash.setWriteSpeed(item.writeSpeed());
                        flash.setComputer(entity);
                        return flash;
                    })
                    .toList()
        );
    }

    @Override
    protected Computer mapToEntity(ComputerDto dto) {
        Computer entity = new Computer();
        updateEntity(entity, dto);
        return entity;
    }
}
