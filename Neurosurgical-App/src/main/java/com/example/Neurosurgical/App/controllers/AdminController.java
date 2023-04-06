package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.exceptions.CannotRemoveLastAdminException;
import com.example.Neurosurgical.App.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.models.entities.ProfessorEntity;
import com.example.Neurosurgical.App.models.entities.UserEntity;

import com.example.Neurosurgical.App.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private final AdminService adminService;


    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Long id) throws UserNotFoundException, CannotRemoveLastAdminException
    {
        adminService.deleteUserById(id);
    }
    @GetMapping(value = "/users" ,produces = "application/json")
    public List<UserEntity> getAll(){

        return adminService.findAll();
    }



}
