package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.models.dtos.*;
import com.example.Neurosurgical.App.advice.exceptions.InvalidDateException;
import com.example.Neurosurgical.App.services.ExamService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
    public ResponseEntity<Void> createExam(@RequestBody @Valid ExamCreationDto examCreationDto) throws InvalidDateException {
        examService.createExam(examCreationDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/evaluate/idStudent={idStudent}", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> evaluateExam(@RequestBody @Valid ExamDto examDto, @PathVariable @Valid Long idStudent) {
        examService.evaluateExam(examDto, idStudent);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/evaluate/idStudent={idStudent}/idQuestion={idQuestion}", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> evaluateLongResponseQuestion(@PathVariable @Valid Long idStudent, @PathVariable @Valid Long idQuestion, @RequestBody @Valid Double points) {
        examService.evaluateLongResponseQuestion(idStudent, idQuestion, points);
        return ResponseEntity.noContent().build();
    }


     @GetMapping(value = "viewExamResult/idExam={idExam}/idStudent={idStudent}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ExamResultDto> viewExamResult(@PathVariable @Valid Long idExam, @PathVariable @Valid Long idStudent) {
        return ResponseEntity.ok(examService.viewExamResult(idExam, idStudent));
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

    @PostMapping(value = "activate/{idExam}", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void activateExam(@PathVariable @Valid @Min(0) Long idExam) {
        examService.activateExam(idExam);
    }

    @DeleteMapping(value = "deactivate/{idExam}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateExam(@PathVariable @Valid @Min(0) Long idExam) {
        examService.deactivateExam(idExam);
    }

}
