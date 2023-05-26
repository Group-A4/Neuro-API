package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.services.DidacticService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/didactic")
@RestController
@CrossOrigin(origins = "http://localhost:3000")

public class DidacticController {
    private final DidacticService didacticService;
    @Autowired
    public DidacticController(DidacticService didacticService) {
        this.didacticService = didacticService;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteDidactic(@PathVariable @Valid @Min(0) Long id){
        didacticService.deleteDidactic(id);
    }

    @DeleteMapping("/delete/course={courseId}/professor={professorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteDidactic(@PathVariable @Valid @Min(0) Long courseId, @PathVariable @Valid @Min(0) Long professorId){
        didacticService.deleteDidactic(courseId, professorId);
    }

    @PostMapping("/create/course={courseId}/professor={professorId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PROFESSOR')")
    public void createDidactic(@PathVariable @Valid @Min(0) Long courseId, @PathVariable @Valid @Min(0) Long professorId){
        didacticService.createDidactic(courseId, professorId);
    }
}
