package com.example.Neurosurgical.App.controllers;


import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.models.dtos.CourseDto;
import com.example.Neurosurgical.App.models.dtos.MaterialCreationDto;
import com.example.Neurosurgical.App.models.dtos.MaterialDto;
import com.example.Neurosurgical.App.models.entities.MaterialEntity;
import com.example.Neurosurgical.App.services.MaterialService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/materials")
@RestController
@CrossOrigin(origins = "http://localhost:3000")

public class MaterialController {
    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping(value = "", produces = "application/json")
    public List<MaterialDto> getAll(){
        return materialService.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Optional<MaterialDto> getById(@PathVariable @Valid @Min(0) Long id) throws EntityNotFoundException {
        return materialService.findById(id);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void deleteMaterialById(@PathVariable @Valid @Min(0) Long id){
        materialService.deleteMaterial(id);
    }

    @PostMapping(value = "/create", produces = "application/json")
    public void createMaterial(@RequestBody @Valid MaterialCreationDto materialCreationDto){
        materialService.createMaterial(materialCreationDto);
    }

    @GetMapping(value = "/title={title}", produces = "application/json")
    public Optional<MaterialDto> getByTitle(@PathVariable @Valid String title) throws EntityNotFoundException {
        return materialService.findByTitle(title);
    }
    @PutMapping("update/{id}")
    public void updateMaterial(@PathVariable @Valid @Min(0) Long id, @RequestBody @Valid MaterialCreationDto materialCreationDto) {
        materialService.updateMaterial(id, materialCreationDto);
    }

    @GetMapping(value = "/course={id}", produces = "application/json")
    public List<MaterialDto> getAllByCourseId(@PathVariable @Valid @Min(0) Long id) throws EntityNotFoundException {
        return materialService.findAllByCourseId(id);
    }

    @GetMapping(value = "/teacher={id}", produces = "application/json")
    public List<MaterialDto> getAllByTeacherId(@PathVariable @Valid @Min(0) Long id) throws EntityNotFoundException {
        return materialService.findAllByTeacherId(id);
    }
}


