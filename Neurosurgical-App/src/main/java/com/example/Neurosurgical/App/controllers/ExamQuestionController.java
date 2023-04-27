package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.advice.exceptions.ExamQuestionNotFoundException;
import com.example.Neurosurgical.App.model.dto.ExamQuestionDto;
import com.example.Neurosurgical.App.services.ExamQuestionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/questionExam")
public class ExamQuestionController {
    private final ExamQuestionService examQuestionService;

    @Autowired
    public ExamQuestionController(ExamQuestionService examQuestionService){
        this.examQuestionService = examQuestionService;
    }

    @GetMapping(value = "", produces="application/json")
    public List<ExamQuestionDto> getAll(){
        return examQuestionService.findAll();
    }

    @GetMapping(value = "/{id}", produces="application/json")
    public Optional<ExamQuestionDto> getById(@PathVariable @Valid @Min(0) Long id) throws ExamQuestionNotFoundException {
        return examQuestionService.findById(id);
    }

    @GetMapping(value = "/professor={id}", produces="application/json")
    public Optional<List<ExamQuestionDto>> getByIdProfessor(@PathVariable @Valid @Min(0) Long id) throws ExamQuestionNotFoundException {
        return examQuestionService.findByIdProfessor(id);
    }

    @GetMapping(value = "/course={id}", produces="application/json")
    public Optional<List<ExamQuestionDto>> getByIdCourse(@PathVariable @Valid @Min(0) Long id) throws ExamQuestionNotFoundException {
        return examQuestionService.findByIdCourse(id);
    }

    @GetMapping(value = "/professor={id}/course={idCourse}", produces="application/json")
    public Optional<List<ExamQuestionDto>> getByIdProfessorAndIdCourse(@PathVariable @Valid @Min(0) Long idProfessor,
                                                                       @PathVariable @Valid @Min(0) Long idCourse) throws ExamQuestionNotFoundException {
        return examQuestionService.findByIdProfessorAndIdCourse(idProfessor, idCourse);
    }

    @PostMapping(value = "/create", produces="application/json")
    public void createExamQuestion(@RequestBody ExamQuestionDto examQuestionDto){
        examQuestionService.createExamQuestion(examQuestionDto);
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public void deleteExamQuestionById(@PathVariable @Valid @Min(0) Long id) {
        examQuestionService.deleteExamQuestionById(id);
    }

    @PutMapping(value = "/update/{id}", produces="application/json")
    public void updateExamQuestion(@PathVariable @Valid @Min(0) Long id, @RequestBody ExamQuestionDto examQuestionDto){
        examQuestionService.updateExamQuestion(id, examQuestionDto);
    }

    @PutMapping(value = "/updateText/{id}", produces="application/json")
    public void updateExamQuestionText(@PathVariable @Valid @Min(0) Long id, @RequestBody String questionText){
        examQuestionService.updateExamQuestionText(id, questionText);
    }
}