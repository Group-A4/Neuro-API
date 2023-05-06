package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.models.dtos.MaterialCreationDto;
import com.example.Neurosurgical.App.models.dtos.MaterialDto;
import com.example.Neurosurgical.App.models.entities.MaterialEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MaterialService{
    List<MaterialDto> findAll();
    void deleteMaterial(Long id);
    Optional<MaterialDto> findById(Long id) throws UserNotFoundException;
    void createMaterial(MaterialCreationDto materialCreationDto) throws UserAlreadyExistsException;
    void updateMaterial(Long id, MaterialCreationDto materialCreationDto);
    Optional<MaterialDto> findByTitle(String title) throws UserNotFoundException;
    List<MaterialDto> findAllByCourseId(Long id);
    List<MaterialDto> findAllByTeacherId(Long id);
    List<MaterialDto> findByMarkdownId(Long id);
}
