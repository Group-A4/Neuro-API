package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.repositories.StudentRepository;
import com.example.Neurosurgical.App.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServices {

    @Autowired
    private final StudentRepository studentRepository;
    @Autowired
    private final UserRepository userRepository;

    public StudentServices(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }


}
