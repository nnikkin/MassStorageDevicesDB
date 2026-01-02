package com.nikkin.devicesdb.Presenters;

import com.nikkin.devicesdb.Dto.FlashDriveDto;
import com.nikkin.devicesdb.Services.FlashDriveService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FlashDrivePresenter {
    private final FlashDriveService flashDriveService;

    public FlashDrivePresenter(FlashDriveService flashDriveService) {
        this.flashDriveService = flashDriveService;
    }

    public List<FlashDriveDto> loadAllFlashDrives() {
        return flashDriveService.getAll();
    }

    public void addFlashDrive(FlashDriveDto dto) {
        flashDriveService.add(dto);
    }

    public void updateFlashDrive(Long id, FlashDriveDto dto) {
        flashDriveService.update(id, dto);
    }

    public void deleteFlashDrives(List<Long> ids) {
        ids.forEach(flashDriveService::delete);
    }

    public FlashDriveDto getFlashDriveById(Long id) {
        return flashDriveService.getById(id)
                .orElseThrow(() -> new RuntimeException("FlashDrive not found"));
    }
}