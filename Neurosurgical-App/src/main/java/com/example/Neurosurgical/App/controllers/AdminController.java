package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.models.entities.UserEntity;

import com.example.Neurosurgical.App.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void deleteUserById(@PathVariable Long id) {
        adminService.deleteUserById(id);
    }
    @GetMapping(value = "/students" ,produces = "application/json")
    public List<UserEntity> getAll(){

        return adminService.findAll();
    }

}
