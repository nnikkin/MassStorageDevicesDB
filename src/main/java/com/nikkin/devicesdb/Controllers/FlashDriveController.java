package com.nikkin.devicesdb.Controllers;

import com.nikkin.devicesdb.Dto.FlashDriveDto;
import com.nikkin.devicesdb.Services.FlashDriveService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/flash")
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
        List<FlashDriveDto> allFlashDriveDtos = flashDriveService.getAll();
        return new ResponseEntity<List<FlashDriveDto>>(allFlashDriveDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlashDriveDto> getFlashDriveById(@PathVariable Long id) {
        FlashDriveDto flashDriveDto = flashDriveService.getById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        return new ResponseEntity<FlashDriveDto>(flashDriveDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FlashDriveDto> deleteFlashDrive(@PathVariable Long id) {
        FlashDriveDto deleted = flashDriveService.delete(id);
        return ResponseEntity.ok(deleted); // 200 OK с телом
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlashDriveDto> replaceFlashDrive(@PathVariable Long id, @RequestBody FlashDriveDto flashDriveDto) {
        FlashDriveDto updatedFlashDriveDto = flashDriveService.update(id, flashDriveDto);
        return new ResponseEntity<FlashDriveDto>(updatedFlashDriveDto, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResponseStatusException.class)
    public String handleException(ResponseStatusException exception) {
        return exception.getMessage();
    }
}
