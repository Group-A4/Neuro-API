package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.model.entity.UserEntity;
import com.example.Neurosurgical.App.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users" ,produces = "application/json")
    public List<UserEntity> getAll(){
        return userService.findAll();
    }
}
