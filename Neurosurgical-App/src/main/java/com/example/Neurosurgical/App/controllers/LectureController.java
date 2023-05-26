package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.models.dtos.LectureCreationDto;
import com.example.Neurosurgical.App.models.entities.LectureEntity;
import com.example.Neurosurgical.App.services.LectureService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/lectures")
public class LectureController {
    private final LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping(value = "", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<LectureEntity>> getAll() {
        return ResponseEntity.ok(lectureService.findAll());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LectureEntity> getById(@PathVariable @Valid @Min(0) Long id) {
        return ResponseEntity.ok(lectureService.findById(id));
    }

    @GetMapping(value = "/title={title}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LectureEntity> getByTitle(@PathVariable @Valid String title) {
        return ResponseEntity.ok(lectureService.findByTitle(title));
    }

    @GetMapping(value = "/course_id={id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<LectureEntity>> getByCourseId(@PathVariable @Valid @Min(0) Long id) {
        return ResponseEntity.ok(lectureService.findAllByCourseId(id));
    }

    @GetMapping(value = "/material_id={id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LectureEntity> getByMaterialId(@PathVariable @Valid @Min(0) Long id) {
        return ResponseEntity.ok(lectureService.findByMaterialId(id));
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PROFESSOR')")
    public ResponseEntity<Void> updateLecture(@PathVariable @Valid @Min(0) Long id, @RequestBody @Valid LectureCreationDto lectureCreationDto) {
        lectureService.updateLecture(id, lectureCreationDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/create", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PROFESSOR')")
    public ResponseEntity<Void> createLecture(@RequestBody @Valid LectureCreationDto lectureCreationDto) {
        lectureService.createLecture(lectureCreationDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PROFESSOR')")
    public ResponseEntity<Void> deleteLecture(@PathVariable @Valid @Min(0) Long id) {
        lectureService.deleteLecture(id);
        return ResponseEntity.noContent().build();
    }
}