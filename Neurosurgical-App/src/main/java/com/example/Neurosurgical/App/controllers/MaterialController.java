package com.example.Neurosurgical.App.controllers;


import com.example.Neurosurgical.App.advice.ErrorResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/materials")
@RestController
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
    public Optional<MaterialDto> getById(@PathVariable @Valid @Min(0) Long id) {
        Optional<MaterialDto> materialDto = materialService.findById(id);
        if (materialDto.isPresent()) {
            return materialDto;
        } else {
            throw new EntityNotFoundException("Material", id);
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMaterialById(@PathVariable @Valid @Min(0) Long id){
        materialService.deleteMaterial(id);
    }

    @PostMapping(value = "/create", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMaterial(@RequestBody @Valid MaterialCreationDto materialCreationDto){
        materialService.createMaterial(materialCreationDto);
    }

    @GetMapping(value = "/title={title}", produces = "application/json")
    public Optional<MaterialDto> getByTitle(@PathVariable @Valid String title) {
        Optional<MaterialDto> materialDto = materialService.findByTitle(title);
        if (materialDto.isPresent()) {
            return materialDto;
        } else {
            throw new EntityNotFoundException("Material" , title);
        }
    }

    @PutMapping("update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMaterial(@PathVariable @Valid @Min(0) Long id, @RequestBody @Valid MaterialCreationDto materialCreationDto) {
        materialService.updateMaterial(id, materialCreationDto);
    }

    @GetMapping(value = "/id_course={id}", produces = "application/json")
    public List<MaterialDto> getAllByCourseId(@PathVariable @Valid @Min(0) Long id) {
        List<MaterialDto> materialDtos = materialService.findAllByCourseId(id);
        if (materialDtos.isEmpty()) {
            throw new EntityNotFoundException("Materials for course id = " + id);
        } else {
            return materialDtos;
        }
    }

    @GetMapping(value = "/id_professor={id}", produces = "application/json")
    public List<MaterialDto> getAllByTeacherId(@PathVariable @Valid @Min(0) Long id) {
        List<MaterialDto> materialDtos = materialService.findAllByTeacherId(id);
        if (materialDtos.isEmpty()) {
            throw new EntityNotFoundException("Materials with teacher id = " + id);
        } else {
            return materialDtos;
        }
    }

    @GetMapping(value = "/id_material_markdown={id}", produces = "application/json")
    public List<MaterialDto> getByMarkdownId(@PathVariable @Valid @Min(0) Long id) {
        List<MaterialDto> materialDtos = materialService.findByMarkdownId(id);
        if (materialDtos.isEmpty()) {
            throw new EntityNotFoundException("Material with markdown id = " + id);
        } else {
            return materialDtos;
        }
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(RuntimeException ex)
    {
        return new ErrorResponse(ex.getMessage());
    }
}


