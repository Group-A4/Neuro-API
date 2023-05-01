package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.models.dtos.ContentCreationDto;
import com.example.Neurosurgical.App.models.entities.ContentEntity;
import com.example.Neurosurgical.App.services.ContentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {

    private final ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentEntity> getContentById(Long id){
        return ResponseEntity.ok(contentService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ContentEntity>> getAllContent(){
        return ResponseEntity.ok(contentService.findAll());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ContentEntity> getContentByName(String name){
        return ResponseEntity.ok(contentService.findByName(name));
    }

    @GetMapping("/professor/{id}")
    public ResponseEntity<List<ContentEntity>> getContentByProfessorId(Long id){
        return ResponseEntity.ok(contentService.findByProfessorId(id));
    }

    @GetMapping("markdown/{id}")
    public ResponseEntity<List<ContentEntity>> getMarkdownContentById(Long id){
        return ResponseEntity.ok(contentService.findByMarkDownId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContentById(Long id){
        contentService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path ="/create" , consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Void> createContent(@ModelAttribute ContentCreationDto content) throws IOException {
        System.out.println(content);
        contentService.createContent(content);
        return ResponseEntity.ok().build();
    }
}
