package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.models.dtos.CourseCreationDto;
import com.example.Neurosurgical.App.models.dtos.CourseDto;
import com.example.Neurosurgical.App.models.entities.CourseEntity;
import com.example.Neurosurgical.App.models.entities.MaterialEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CourseService {
    List<CourseDto> findAll();
    void deleteCourse(Long id);
    Optional<CourseDto> findById(Long id) throws UserNotFoundException;
    void createCourse(CourseCreationDto courseCreationDto) throws UserAlreadyExistsException;
    void updateCourse(Long id, CourseCreationDto courseCreationDto);
    Optional<CourseDto> findByCode(String code) throws UserNotFoundException;
    Optional<CourseDto> findByTitle(String code) throws UserNotFoundException;
    Optional<CourseDto> findByMaterialId(Long id) throws UserNotFoundException;
    List<CourseDto> findAllByProfessorId(Long id);
    List<CourseDto> findAllByStudentId(Long id);
}
