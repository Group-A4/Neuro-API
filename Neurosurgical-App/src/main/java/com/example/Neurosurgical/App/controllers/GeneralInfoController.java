package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.dtos.GeneralInfoDto;
import com.example.Neurosurgical.App.services.GeneralInfoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/general-info")
public class GeneralInfoController {
    private final GeneralInfoService generalInfoService;

    @Autowired
    public GeneralInfoController(GeneralInfoService generalInfoService) {
        this.generalInfoService = generalInfoService;
    }

    @GetMapping(value="/{id}", produces = "application/json")
    public Optional<GeneralInfoDto> getGeneralInfoById(@PathVariable @Valid @Min(0) Long id) throws EntityNotFoundException {
        return generalInfoService.findById(id);
    }

    @PutMapping("update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void updateGeneralInfo(@PathVariable @Valid @Min(0) Long id, @RequestBody @Valid GeneralInfoDto generalInfoDto) {
        generalInfoService.updateGeneralInfo(id, generalInfoDto);
    }
}
