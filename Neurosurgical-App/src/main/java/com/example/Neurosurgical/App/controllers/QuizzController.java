package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.services.QuizzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quizzes")
public class QuizzController {

    private final QuizzService quizzService;

    @Autowired
    public QuizzController(QuizzService quizzService) {
        this.quizzService = quizzService;
    }



}
