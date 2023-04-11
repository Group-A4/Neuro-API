package com.example.Neurosurgical.App.controllers;


import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.model.dto.ProfessorCreationDto;
import com.example.Neurosurgical.App.model.dto.ProfessorDto;
import com.example.Neurosurgical.App.services.ProfessorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professors")
public class ProfessorController {
    private final ProfessorService professorService;

    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }


    @GetMapping(value = "", produces = "application/json")
    public List<ProfessorDto> getAll(){
        return professorService.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Optional<ProfessorDto> getById(@Valid @Min(0) @PathVariable Long id) throws UserNotFoundException {
        return professorService.findById(id);
    }

    @GetMapping(value = "/code/{code}", produces = "application/json")
    public Optional<ProfessorDto> getByCode(@Valid @Min(0) @PathVariable String code) throws UserNotFoundException {
        return professorService.findByCode(code);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void deleteUserById(@PathVariable @Valid @Min(0) Long id){
        professorService.deleteProfessor(id);
    }

    @PostMapping(value = "/create", produces = "application/json")
    public void createProfessor(@RequestBody @Valid ProfessorCreationDto professorCreationDto) throws UserAlreadyExistsException {
        professorService.createProfessor(professorCreationDto);
    }

    @PutMapping("update/{id}")
    public void updateProfessor(@PathVariable @Valid @Min(0) Long id, @RequestBody @Valid ProfessorDto professorDto) {
        professorService.updateProfessor(id, professorDto);
    }
}
