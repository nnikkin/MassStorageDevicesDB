package com.nikkin.devicesdb.Presenters;

import com.nikkin.devicesdb.Dto.HardDiskDriveDto;
import com.nikkin.devicesdb.Services.HardDiskDriveService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HardDiskDrivePresenter {
    private final HardDiskDriveService hardDiskDriveService;

    public HardDiskDrivePresenter(HardDiskDriveService hardDiskDriveService) {
        this.hardDiskDriveService = hardDiskDriveService;
    }

    public List<HardDiskDriveDto> loadAllHdd() {
        return hardDiskDriveService.getAll();
    }

    public void addHdd(HardDiskDriveDto dto) {
        hardDiskDriveService.add(dto);
    }

    public void updateHdd(Long id, HardDiskDriveDto dto) {
        hardDiskDriveService.update(id, dto);
    }

    public void deleteHdd(List<Long> ids) {
        ids.forEach(hardDiskDriveService::delete);
    }

    public HardDiskDriveDto getHddById(Long id) {
        return hardDiskDriveService.getById(id)
                .orElseThrow(() -> new RuntimeException("HardDiskDrive not found"));
    }
}