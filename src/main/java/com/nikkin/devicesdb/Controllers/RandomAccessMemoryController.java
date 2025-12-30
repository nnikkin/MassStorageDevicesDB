package com.nikkin.devicesdb.Controllers;

import com.nikkin.devicesdb.Dto.RandomAccessMemoryDto;
import com.nikkin.devicesdb.Services.RandomAccessMemoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/ram")
public class RandomAccessMemoryController {
    private final RandomAccessMemoryService ramService;

    public RandomAccessMemoryController(RandomAccessMemoryService ramService) {
        this.ramService = ramService;
    }

    @PostMapping
    public ResponseEntity<RandomAccessMemoryDto> addRandomAccessMemory(@RequestBody RandomAccessMemoryDto ramDto) {
        RandomAccessMemoryDto createdRamDto = ramService.add(ramDto);
        return new ResponseEntity<RandomAccessMemoryDto>(createdRamDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RandomAccessMemoryDto>> getAllRandomAccessMemorys() {
        List<RandomAccessMemoryDto> allRamDtos = ramService.getAll();
        return new ResponseEntity<List<RandomAccessMemoryDto>>(allRamDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RandomAccessMemoryDto> getRandomAccessMemoryById(@PathVariable Long id) {
        RandomAccessMemoryDto ramDto = ramService.getById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        return new ResponseEntity<RandomAccessMemoryDto>(ramDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RandomAccessMemoryDto> deleteRandomAccessMemory(@PathVariable Long id) {
        RandomAccessMemoryDto deleted = ramService.delete(id);
        return ResponseEntity.ok(deleted); // 200 OK с телом
    }

    @PutMapping("/{id}")
    public ResponseEntity<RandomAccessMemoryDto> replaceRandomAccessMemory(@PathVariable Long id, @RequestBody RandomAccessMemoryDto ramDto) {
        RandomAccessMemoryDto updatedRamDto = ramService.update(id, ramDto);
        return new ResponseEntity<RandomAccessMemoryDto>(updatedRamDto, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleException(EntityNotFoundException exception) {
        return exception.getMessage();
    }
}
