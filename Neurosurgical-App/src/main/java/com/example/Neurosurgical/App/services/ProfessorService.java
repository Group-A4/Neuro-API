package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.models.dtos.ProfessorCreationDto;
import com.example.Neurosurgical.App.models.dtos.ProfessorDto;
import com.example.Neurosurgical.App.models.entities.CourseEntity;
import com.example.Neurosurgical.App.models.entities.MaterialEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProfessorService {
    List<ProfessorDto> findAll();
    void deleteProfessor(Long id);
    Optional<ProfessorDto> findById(Long id);
    void createProfessor(ProfessorCreationDto professorCreationDto);
    void updateProfessor(Long id, ProfessorDto professorDto);
    Optional<ProfessorDto> findByCode(String code);
    List<ProfessorDto> findByCourseId(Long id);
    Optional<ProfessorDto> findByMaterialId(Long id);
}
