package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.model.dto.ProfessorCreationDto;
import com.example.Neurosurgical.App.model.dto.ProfessorDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProfessorService {
    List<ProfessorDto> findAll();
    void deleteProfessor(Long id);
    Optional<ProfessorDto> findById(Long id) throws UserNotFoundException;
    void createProfessor(ProfessorCreationDto professorCreationDto) throws UserAlreadyExistsException;
    void updateProfessor(Long id, ProfessorDto professorDto);
    Optional<ProfessorDto> findByCode(String code) throws UserNotFoundException;
}
