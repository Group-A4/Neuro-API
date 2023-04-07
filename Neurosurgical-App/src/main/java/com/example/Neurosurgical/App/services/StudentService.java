package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.model.dto.StudentCreationDto;
import com.example.Neurosurgical.App.model.dto.StudentDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface StudentService {
    List<StudentDto> findAll();

    void deleteStudent(Long id);

    Optional<StudentDto> findById(Long id) throws UserNotFoundException;
    void createStudent(StudentCreationDto studentCreationDto) throws UserAlreadyExistsException;
    Optional<StudentDto> findByCode(String code) throws UserNotFoundException;
}
