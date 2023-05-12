package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.dtos.ExamDto;
import com.example.Neurosurgical.App.models.dtos.ExamQuestionDto;
import com.example.Neurosurgical.App.services.ExamService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/exam")
public class ExamController {
    private final ExamService examService;

    @Autowired
    public ExamController(ExamService examService){
        this.examService = examService;
    }

    @GetMapping(value = "/exam={id}", produces = "application/json")
    public Optional<List<ExamQuestionDto>> getExamById(@PathVariable @Valid @Min(0) Long id) throws EntityNotFoundException {
        return examService.findById(id);
    }
    
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void deleteExamQuestionById(@PathVariable @Valid @Min(0) Long id) {
        examService.deleteExamById(id);
    }

    @GetMapping(value = "/professor={id}", produces = "application/json")
    public Optional<List<ExamDto>> getExamByProfessorID(@PathVariable @Valid @Min(0) Long id) throws EntityNotFoundException{
        return examService.findByProfessorId(id);
    }

//    @PostMapping(value = "/create", produces = "application/json")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void createExam(@RequestBody @Valid ExamDto examDto){
//        examService.createExam(examDto);
//    }

//    @GetMapping(value = "/sumrExam={code}", produces = "application/json")
//    public Optional<SumrExamDto> getExamSumrByCode(@PathVariable @Valid @Min(0) Long id) throws EntityNotFoundException) {
//
//    }
}
