package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.models.dtos.ContentCreationDto;
import com.example.Neurosurgical.App.models.entities.ContentEntity;
import com.example.Neurosurgical.App.services.ContentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ContentEntity> getContentById(@PathVariable @Valid @Min(0)  Long id){
        return ResponseEntity.ok(contentService.findById(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ContentEntity>> getAllContent(){
        return ResponseEntity.ok(contentService.findAll());
    }

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ContentEntity> getContentByName(@PathVariable @Valid String name){
        return ResponseEntity.ok(contentService.findByName(name));
    }

    @GetMapping("/professor/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ContentEntity>> getContentByProfessorId(@PathVariable @Valid @Min(0) Long id){
        return ResponseEntity.ok(contentService.findByProfessorId(id));
    }

    @GetMapping("markdown/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ContentEntity>> getMarkdownContentById(@PathVariable @Valid @Min(0) Long id){
        return ResponseEntity.ok(contentService.findByMarkDownId(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteContentById(@PathVariable @Valid @Min(0) Long id){
        contentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path ="/create" , consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createContent(@ModelAttribute ContentCreationDto content) throws IOException {
        contentService.createContent(content);
        return ResponseEntity.created(null).build();
    }


}
