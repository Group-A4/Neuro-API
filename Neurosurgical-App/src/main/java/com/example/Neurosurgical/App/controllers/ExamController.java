package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.models.dtos.ExamCreationDto;
import com.example.Neurosurgical.App.models.dtos.ExamDto;
import com.example.Neurosurgical.App.models.dtos.ExamSummariseDto;
import com.example.Neurosurgical.App.models.dtos.LectureCreationDto;
import com.example.Neurosurgical.App.services.ExamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/exam")
public class ExamController {

    private final ExamService examService;

    @Autowired
    public ExamController(ExamService examService){
        this.examService = examService;
    }

    @PostMapping(value = "/create", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createLecture(@RequestBody @Valid ExamCreationDto examCreationDto) {
        examService.createExam(examCreationDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/code={code}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ExamDto> findByCode(@PathVariable @Valid String code) {
        return ResponseEntity.ok(examService.findByCode(code));
    }

    @GetMapping(value = "/summarise/idStudent={idStudent}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ExamSummariseDto>> findByIdStudent(@PathVariable @Valid Long idStudent) {
        return ResponseEntity.ok(examService.findByIdStudent(idStudent));
    }

    @GetMapping(value = "/summarise/idCourse={idCourse}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ExamSummariseDto>> findByIdCourse(@PathVariable @Valid Long idCourse) {
        return ResponseEntity.ok(examService.findByIdCourse(idCourse));
    }


}
