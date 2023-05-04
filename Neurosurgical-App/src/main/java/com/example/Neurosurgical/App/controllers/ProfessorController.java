package com.example.Neurosurgical.App.controllers;


import com.example.Neurosurgical.App.advice.ErrorResponse;
import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.models.dtos.ProfessorCreationDto;
import com.example.Neurosurgical.App.models.dtos.ProfessorDto;
import com.example.Neurosurgical.App.models.entities.CourseEntity;
import com.example.Neurosurgical.App.models.entities.MaterialEntity;
import com.example.Neurosurgical.App.services.ProfessorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/professors")
public class ProfessorController {
    private final ProfessorService professorService;

    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }


    @GetMapping(value = "", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<ProfessorDto> getAll(){
        return professorService.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ProfessorDto> getById(@Valid @Min(0) @PathVariable Long id) throws UserNotFoundException {
        Optional<ProfessorDto> professorDto = professorService.findById(id);
        if(professorDto.isPresent()){
            return professorDto;
        } else {
            throw new UserNotFoundException();
        }
    }

    @GetMapping(value = "/code/{code}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ProfessorDto> getByCode(@Valid @Min(0) @PathVariable String code) throws UserNotFoundException {
        Optional<ProfessorDto> professorDto = professorService.findByCode(code);
        if(professorDto.isPresent()){
            return professorDto;
        } else {
            throw new UserNotFoundException();
        }
    }

    @DeleteMapping(value = "delete/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable @Valid @Min(0) Long id){
        professorService.deleteProfessor(id);
    }

    @PostMapping(value = "/create", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProfessor(@RequestBody @Valid ProfessorCreationDto professorCreationDto) throws UserAlreadyExistsException {
        professorService.createProfessor(professorCreationDto);
    }

    @PutMapping("update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfessor(@PathVariable @Valid @Min(0) Long id, @RequestBody @Valid ProfessorDto professorDto) {
        professorService.updateProfessor(id, professorDto);
    }

    @GetMapping(value = "/course={id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<ProfessorDto> getByCourseId(@PathVariable @Valid @Min(0) Long id) throws UserNotFoundException {
        List<ProfessorDto> professorDto = professorService.findByCourseId(id);
        if(professorDto.isEmpty()){
            throw new UserNotFoundException();
        } else {
            return professorDto;
        }
    }

    @GetMapping(value = "/material={id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ProfessorDto> getByMaterialId(@PathVariable @Valid @Min(0) Long id) throws UserNotFoundException {
        Optional<ProfessorDto> professorDto = professorService.findByMaterialId(id);
        if(professorDto.isPresent()){
            return professorDto;
        } else {
            throw new UserNotFoundException();
        }
    }
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(RuntimeException ex)
    {
        return new ErrorResponse(ex.getMessage());
    }
}
