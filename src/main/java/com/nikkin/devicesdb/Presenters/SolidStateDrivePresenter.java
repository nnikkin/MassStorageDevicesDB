package com.nikkin.devicesdb.Presenters;

import com.nikkin.devicesdb.Dto.SolidStateDriveDto;
import com.nikkin.devicesdb.Services.SolidStateDriveService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SolidStateDrivePresenter {
    private final SolidStateDriveService solidStateDriveService;

    public SolidStateDrivePresenter(SolidStateDriveService solidStateDriveService) {
        this.solidStateDriveService = solidStateDriveService;
    }

    public List<SolidStateDriveDto> loadAllSsd() {
        return solidStateDriveService.getAll();
    }

    public void addSsd(SolidStateDriveDto dto) {
        solidStateDriveService.add(dto);
    }

    public void updateSsd(Long id, SolidStateDriveDto dto) {
        solidStateDriveService.update(id, dto);
    }

    public void deleteSsd(List<Long> ids) {
        ids.forEach(solidStateDriveService::delete);
    }

    public SolidStateDriveDto getSsdById(Long id) {
        return solidStateDriveService.getById(id)
                .orElseThrow(() -> new RuntimeException("SolidStateDrive not found"));
    }
}