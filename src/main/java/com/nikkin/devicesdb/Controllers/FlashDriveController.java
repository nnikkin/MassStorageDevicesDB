package com.nikkin.devicesdb.Controllers;

import com.nikkin.devicesdb.Dto.FlashDriveDto;
import com.nikkin.devicesdb.Services.FlashDriveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/FlashDrives")
public class FlashDriveController {
    private final FlashDriveService flashDriveService;

    public FlashDriveController(FlashDriveService flashDriveService) {
        this.flashDriveService = flashDriveService;
    }

    @PostMapping
    public ResponseEntity<FlashDriveDto> addFlashDrive(@RequestBody FlashDriveDto flashDriveDto) {
        FlashDriveDto createdFlashDriveDto = flashDriveService.add(flashDriveDto);
        return new ResponseEntity<FlashDriveDto>(createdFlashDriveDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FlashDriveDto>> getAllFlashDrives() {
        List<FlashDriveDto> allFlashDriveDtos = flashDriveService.getAllFlashDrives();
        return new ResponseEntity<List<FlashDriveDto>>(allFlashDriveDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlashDriveDto> getFlashDriveById(@PathVariable Long id) {
        FlashDriveDto flashDriveDto = flashDriveService.getFlashDriveById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        return new ResponseEntity<FlashDriveDto>(flashDriveDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FlashDriveDto> deleteFlashDrive(@PathVariable Long id) {
        FlashDriveDto deleted = flashDriveService.deleteFlashDrive(id);
        return ResponseEntity.ok(deleted); // 200 OK с телом
    }

    @PutMapping("/{id}")
    public void replaceFlashDrive() {}

    @PatchMapping("/{id}")
    public void updateFlashDrive() {}
}
