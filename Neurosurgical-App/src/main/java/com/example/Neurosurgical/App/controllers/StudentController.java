//package com.example.Neurosurgical.App.controllers;
//
//
//import com.example.Neurosurgical.App.model.dto.UserDto;
//import com.example.Neurosurgical.App.model.entity.StudentEntity;
//import com.example.Neurosurgical.App.services.StudentService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/students")
//public class StudentController {
//    private final StudentService studentService;
//
//    public StudentController(StudentService studentService) {
//        this.studentService = studentService;
//    }
//
//
//    @GetMapping(value = "" ,produces = "application/json")
//    public List<StudentEntity> getAll(){
//        return studentService.findAll();
//    }
//
//
//}
