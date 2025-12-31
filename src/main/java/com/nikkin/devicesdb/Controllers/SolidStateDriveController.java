package com.nikkin.devicesdb.Controllers;

import com.nikkin.devicesdb.Dto.SolidStateDriveDto;
import com.nikkin.devicesdb.Services.SolidStateDriveService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/ssd")
public class SolidStateDriveController {
    private final SolidStateDriveService ssdService;

    public SolidStateDriveController(SolidStateDriveService ssdService) {
        this.ssdService = ssdService;
    }

    @PostMapping
    public ResponseEntity<SolidStateDriveDto> addSolidStateDrive(@RequestBody @Valid SolidStateDriveDto ssdDto) {
        SolidStateDriveDto createdSsdDto = ssdService.add(ssdDto);
        return new ResponseEntity<SolidStateDriveDto>(createdSsdDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SolidStateDriveDto>> getAllSolidStateDrives() {
        List<SolidStateDriveDto> allSsdDtos = ssdService.getAll();
        return new ResponseEntity<List<SolidStateDriveDto>>(allSsdDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolidStateDriveDto> getSolidStateDriveById(@PathVariable Long id) {
        SolidStateDriveDto ssdDto = ssdService.getById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        return new ResponseEntity<SolidStateDriveDto>(ssdDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SolidStateDriveDto> deleteSolidStateDrive(@PathVariable Long id) {
        SolidStateDriveDto deleted = ssdService.delete(id);
        return ResponseEntity.ok(deleted); // 200 OK с телом
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolidStateDriveDto> replaceSolidStateDrive(@PathVariable Long id, @RequestBody @Valid SolidStateDriveDto ssdDto) {
        SolidStateDriveDto updatedSsdDto = ssdService.update(id, ssdDto);
        return new ResponseEntity<SolidStateDriveDto>(updatedSsdDto, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleException(EntityNotFoundException exception) {
        return exception.getMessage();
    }
}
