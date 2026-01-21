package com.nikkin.devicesdb.Controllers;

import com.nikkin.devicesdb.Dto.FlashDriveDto;
import com.nikkin.devicesdb.Services.FlashDriveService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/flash")
@Validated
public class FlashDriveController {
    @Autowired
    private FlashDriveService flashDriveService;

    public FlashDriveController(FlashDriveService flashDriveService) {
        this.flashDriveService = flashDriveService;
    }

    @PostMapping
    public ResponseEntity<FlashDriveDto> addFlashDrive(@RequestBody @Valid FlashDriveDto flashDriveDto) {
        FlashDriveDto createdFlashDriveDto = flashDriveService.add(flashDriveDto);
        return new ResponseEntity<>(createdFlashDriveDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FlashDriveDto>> getAllFlashDrives() {
        List<FlashDriveDto> allFlashDriveDtos = flashDriveService.getAll();
        return ResponseEntity.ok(allFlashDriveDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlashDriveDto> getFlashDriveById(@PathVariable Integer id) {
        //FlashDriveDto flashDriveDto = flashDriveService.getById(id)
        //        .orElseThrow(() -> new EntityNotFoundException("Запись не найдена"));
        //return ResponseEntity.ok(flashDriveDto);
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FlashDriveDto> deleteFlashDrive(@PathVariable Integer id) {
        FlashDriveDto deleted = flashDriveService.delete(id);
        return ResponseEntity.ok(deleted); // 200 OK с телом
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlashDriveDto> replaceFlashDrive(@PathVariable Integer id, @RequestBody @Valid FlashDriveDto flashDriveDto) {
        FlashDriveDto updatedFlashDriveDto = flashDriveService.update(id, flashDriveDto);
        return ResponseEntity.ok(updatedFlashDriveDto);
    }
}
