package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.dtos.ContentCreationDto;
import com.example.Neurosurgical.App.models.entities.ContentEntity;
import com.example.Neurosurgical.App.models.entities.ContentType;
import com.example.Neurosurgical.App.models.entities.MarkdownContentEntity;
import com.example.Neurosurgical.App.repositories.ContentRepository;
import com.example.Neurosurgical.App.repositories.MarkdownContentRepository;
import com.example.Neurosurgical.App.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContentServiceImpl implements ContentService{

    private final ContentRepository contentRepository;
    private final MarkdownContentRepository markdownContentRepository;

    private final ProfessorRepository professorRepository;
    private final StorageService storageService;

    @Autowired
    public ContentServiceImpl(ContentRepository contentRepository, MarkdownContentRepository markdownContentRepository, ProfessorRepository professorRepository, StorageService storageService) {
        this.contentRepository = contentRepository;
        this.markdownContentRepository = markdownContentRepository;
        this.professorRepository = professorRepository;
        this.storageService = storageService;
    }

    @Override
    public void createContent(ContentCreationDto contentCreationDto) throws IOException {
        String containerName = "professor"+contentCreationDto.getProfessorId();
        storageService.createContainer(containerName);

        storageService.uploadFile(containerName, contentCreationDto.getFileName(),contentCreationDto.getContentFile().getBytes());
        System.out.println(contentCreationDto.getContentFile().getBytes().length);

        String link = "https://neuroapi.blob.core.windows.net/"+containerName+"/"+contentCreationDto.getFileName();


        ContentEntity contentEntity = ContentEntity.builder()
                .name(contentCreationDto.getFileName())
                .link(link)
                .type(ContentType.valueOf(contentCreationDto.getType().toUpperCase().trim()))
                .professor(professorRepository.getById(contentCreationDto.getProfessorId()))
                .build();

        contentRepository.save(contentEntity);
    }

    @Override
    public void deleteById(Long id) {
        ContentEntity content = contentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Content", id));
        String containerName = "professor"+content.getProfessor().getIdUser();
        storageService.deleteFile(containerName, content.getName());
        contentRepository.deleteById(id);
    }

    @Override
    public List<ContentEntity> findAll() {
        List<ContentEntity> contentEntities = Optional.ofNullable(contentRepository.findAll()).orElseThrow(() -> new EntityNotFoundException("Content not found"));
        return contentEntities;
    }

    public List<ContentEntity> findByProfessorId(Long id){
        List<ContentEntity> contentEntities = Optional.ofNullable(contentRepository.findByProfessorId(id))
                .orElseThrow(() -> new EntityNotFoundException("Content ", id));
        return contentEntities;
    }
    @Override
    public ContentEntity findById(Long id) {
        ContentEntity contentEntity = contentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Content", id));
        return contentEntity;
    }

    @Override
    public ContentEntity findByName(String name) {
        ContentEntity contentEntity = contentRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Content", name));
        return contentEntity;
    }

    @Override
    public List<ContentEntity> findByMarkDownId(Long markDownId) {
        List<MarkdownContentEntity> markdownContentEntities = Optional.ofNullable(markdownContentRepository.findByMarkdownId(markDownId))
                .orElseThrow(() -> new EntityNotFoundException("content with markdown id : "+ markDownId));
        List<ContentEntity> contentEntities = markdownContentEntities.stream()
                .map(MarkdownContentEntity::getContent).
                collect(Collectors.toList());

        return contentEntities;
    }
}
