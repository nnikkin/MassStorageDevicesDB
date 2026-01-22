package com.nikkin.devicesdb.Controllers;

import com.nikkin.devicesdb.Dto.ComputerDto;
import com.nikkin.devicesdb.Services.ComputerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/computers")
@Validated
public class ComputerController {
    @Autowired
    private ComputerService computerService;

    public ComputerController(ComputerService computerService) {
        this.computerService = computerService;
    }

    @PostMapping
    public ResponseEntity<ComputerDto> addComputer(@RequestBody @Valid ComputerDto computerDto) {
        ComputerDto createdComputerDto = computerService.add(computerDto);
        return new ResponseEntity<>(createdComputerDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ComputerDto>> getAllComputers() {
        List<ComputerDto> allComputerDtos = computerService.getAll();
        return ResponseEntity.ok(allComputerDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComputerDto> getComputerById(@PathVariable Integer id) {
        ComputerDto computerDto = computerService.getById(id);
        return ResponseEntity.ok(computerDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ComputerDto> deleteComputer(@PathVariable Integer id) {
        ComputerDto deleted = computerService.delete(id);
        return ResponseEntity.ok(deleted); // 200 OK с телом
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComputerDto> replaceComputer(@PathVariable Integer id, @RequestBody @Valid ComputerDto computerDto) {
        ComputerDto updatedComputerDto = computerService.update(id, computerDto);
        return ResponseEntity.ok(updatedComputerDto);
    }
}
