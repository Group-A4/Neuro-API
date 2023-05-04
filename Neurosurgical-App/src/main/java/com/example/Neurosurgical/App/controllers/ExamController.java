package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.dtos.ExamQuestionDto;
import com.example.Neurosurgical.App.services.ExamService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/exam")
public class ExamController {
    private final ExamService examService;

    @Autowired
    public ExamController(ExamService examService){
        this.examService = examService;
    }

    @GetMapping(value = "/exam={id}", produces = "application/json")
    public Optional<List<ExamQuestionDto>> getExamByCourseId(@PathVariable @Valid @Min(0) Long id) throws EntityNotFoundException {
        return examService.findById(id);
    }
}
