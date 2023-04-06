package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.exceptions.CannotRemoveLastAdminException;
import com.example.Neurosurgical.App.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.models.dtos.UserAdminDTO;
import com.example.Neurosurgical.App.models.dtos.UserProfessorDTO;
import com.example.Neurosurgical.App.models.dtos.UserStudentDTO;
import com.example.Neurosurgical.App.models.entities.UserEntity;
import com.example.Neurosurgical.App.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Long id) throws UserNotFoundException, CannotRemoveLastAdminException
    {
        adminService.deleteUserById(id);
    }
    @GetMapping(value = "/users" ,produces = "application/json")
    public List<UserEntity> getAll(){

        return adminService.findAll();
    }

    @GetMapping(value = "/student/{id}", produces = "application/json")
    public UserStudentDTO findUser(@PathVariable Long id) throws UserNotFoundException {
        return adminService.findUserStudentById(id);
    }

    @PostMapping(value = "/updateStudent", produces = "application/json")
    public void updateStudent(@RequestBody UserStudentDTO userStudentDTO) {
        adminService.updateStudent(userStudentDTO);
    }



    @GetMapping(value = "/all/{id}" ,produces = "application/json")
    public UserEntity getStudentById(@PathVariable Long id){
        return adminService.findById(id);
    }

    @PostMapping(value = "/createAccount/student", produces = "application/json")
    public void createStudent(@RequestBody UserStudentDTO userStudentDTO) {
        adminService.createAccountStudent(userStudentDTO);
    }

    @PostMapping(value = "/createAccount/professor", produces = "application/json")
    public void createAccountProfessor(@RequestBody UserProfessorDTO userProfessorDTO) {
        adminService.createAccountProfessor(userProfessorDTO);
    }

    @PostMapping(value = "/createAccount/admin", produces = "application/json")
    public void createAccountAdmin(@RequestBody UserAdminDTO userAdminDTO) {
        adminService.createAccountAdmin(userAdminDTO);
    }


}

