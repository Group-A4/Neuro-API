package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.models.dtos.QuestionExamDto;
import com.example.Neurosurgical.App.services.QuestionExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/questionExam")
@CrossOrigin(origins = "http://localhost:3000")
public class QuestionExamController {
    private final QuestionExamService questionExamService;

    @Autowired
    public QuestionExamController(QuestionExamService questionExamService){
        this.questionExamService = questionExamService;
    }

    @GetMapping(value = "", produces="application/json")
    public List<QuestionExamDto> getAll() {
        return questionExamService.findAll();
    }

}
