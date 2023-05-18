package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.models.dtos.ContentCreationDto;
import com.example.Neurosurgical.App.models.entities.ContentEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface ContentService {
    void createContent(ContentCreationDto contentCreationDto) throws IOException;
    void deleteById(Long id);
    List<ContentEntity> findAll();
    List<ContentEntity> findByProfessorId(Long id);
    ContentEntity findById(Long id);
    ContentEntity findByName(String name);
    List<ContentEntity> findByMarkDownId(Long id);

}
