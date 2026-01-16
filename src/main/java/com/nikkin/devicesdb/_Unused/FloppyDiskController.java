package com.nikkin.devicesdb._Unused;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/floppy")
public class FloppyDiskController {
    private final FloppyDiskService floppyDiskService;

    public FloppyDiskController(FloppyDiskService floppyDiskService) {
        this.floppyDiskService = floppyDiskService;
    }

    @PostMapping
    public ResponseEntity<FloppyDiskDto> addFloppyDisk(@RequestBody @Valid FloppyDiskDto floppyDiskDto) {
        FloppyDiskDto createdFloppyDiskDto = floppyDiskService.add(floppyDiskDto);
        return new ResponseEntity<>(createdFloppyDiskDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FloppyDiskDto>> getAllFloppyDisks() {
        List<FloppyDiskDto> allFloppyDiskDtos = floppyDiskService.getAll();
        return new ResponseEntity<>(allFloppyDiskDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FloppyDiskDto> getFloppyDiskById(@PathVariable Long id) {
        FloppyDiskDto floppyDiskDto = floppyDiskService.getById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        return new ResponseEntity<>(floppyDiskDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FloppyDiskDto> deleteFloppyDisk(@PathVariable Long id) {
        FloppyDiskDto deleted = floppyDiskService.delete(id);
        return ResponseEntity.ok(deleted); // 200 OK с телом
    }

    @PutMapping("/{id}")
    public ResponseEntity<FloppyDiskDto> replaceFloppyDisk(@PathVariable Long id, @RequestBody @Valid FloppyDiskDto floppyDiskDto) {
        FloppyDiskDto updatedFloppyDiskDto = floppyDiskService.update(id, floppyDiskDto);
        return new ResponseEntity<>(updatedFloppyDiskDto, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleException(EntityNotFoundException exception) {
        return exception.getMessage();
    }
}
