package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.mappers.MaterialMapper;
import com.example.Neurosurgical.App.models.dtos.MaterialCreationDto;
import com.example.Neurosurgical.App.models.dtos.MaterialDto;
import com.example.Neurosurgical.App.models.entities.*;
import com.example.Neurosurgical.App.repositories.CourseRepository;
import com.example.Neurosurgical.App.repositories.MaterialRepository;
import com.example.Neurosurgical.App.repositories.MaterialsMarkdownRepository;
import com.example.Neurosurgical.App.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;
    private final MaterialsMarkdownRepository materialsMarkdownRepository;
    private final ContentServiceImpl contentService;

    @Autowired
    public MaterialServiceImpl(MaterialRepository materialRepository, CourseRepository courseRepository, ProfessorRepository professorRepository, MaterialsMarkdownRepository materialsMarkdownRepository, ContentServiceImpl contentService) {
        this.materialRepository = materialRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
        this.materialsMarkdownRepository = materialsMarkdownRepository;
        this.contentService = contentService;
    }

    @Override
    public List<MaterialDto> findAll() {
        List<MaterialEntity> materialEntities = materialRepository.findAll();
        List<MaterialsMarkdownEntity> materialsMarkdownEntities = materialsMarkdownRepository.findAll();

        List<MaterialDto> materialDtos = new ArrayList<>();

        for (MaterialEntity materialEntity : materialEntities) {
            for (MaterialsMarkdownEntity materialsMarkdownEntity : materialsMarkdownEntities) {
                if(materialEntity.getMaterialMarkdown().getId().equals(materialsMarkdownEntity.getId())){
                    materialDtos.add(MaterialMapper.toDto(materialEntity, materialsMarkdownEntity.getMarkdownText(), materialsMarkdownEntity.getHtml()));
                }
            }
        }


        return materialDtos;
    }

    @Override
    public void deleteMaterial(Long id) {
        checkIfExists(id);
        materialRepository.deleteById(id);
    }

    @Override
    public Optional<MaterialDto> findById(Long id) throws UserNotFoundException {
        MaterialEntity materialEntity = materialRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Material",id));

        String markdownText = materialEntity.getMaterialMarkdown().getMarkdownText();
        String html = materialEntity.getMaterialMarkdown().getHtml();

        MaterialDto materialDto = MaterialMapper.toDto(materialEntity, markdownText, html);

        return Optional.of(materialDto);
    }

    @Override
    public void createMaterial(MaterialCreationDto materialCreationDto) throws UserAlreadyExistsException {
        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(materialCreationDto.getIdCourse());
        Optional<ProfessorEntity> professorEntityOptional = professorRepository.findById(materialCreationDto.getIdProfessor());

        if(courseEntityOptional.isEmpty()) throw new EntityNotFoundException("course", materialCreationDto.getIdCourse());
        if(professorEntityOptional.isEmpty()) throw new EntityNotFoundException("professor", materialCreationDto.getIdProfessor());

        // de la front trebuie sa primim un markdown cu toate escape-urile necesare
//        List<ContentEntity> contentEntities = contentService.findByProfessorId(materialCreationDto.getIdProfessor());
//
//        MarkdownToHtmlParserService markdownParserService = new MarkdownToHtmlParserServiceImpl("neuroapi", "professor" + materialCreationDto.getIdProfessor(), contentEntities);
//        String html = markdownParserService.parse(materialCreationDto.getMarkdownText());

        MaterialsMarkdownEntity materialsMarkdownEntity = MaterialsMarkdownEntity.builder()
                .markdownText(materialCreationDto.getMarkdownText())
                .html(materialCreationDto.getHtml())
                .build();

        materialsMarkdownRepository.save(materialsMarkdownEntity);

        MaterialEntity materialEntity = MaterialEntity.builder()
                .course(courseEntityOptional.get())
                .professor(professorEntityOptional.get())
                .title(materialCreationDto.getTitle())
                .materialMarkdown(materialsMarkdownEntity)
                .build();

        materialRepository.save(materialEntity);
    }

    @Override
    public void updateMaterial(Long id, MaterialCreationDto materialCreationDto) throws UserNotFoundException {
        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(materialCreationDto.getIdCourse());
        Optional<ProfessorEntity> professorEntityOptional = professorRepository.findById(materialCreationDto.getIdProfessor());


        if(courseEntityOptional.isEmpty()) throw new EntityNotFoundException("course", materialCreationDto.getIdCourse());
        if(professorEntityOptional.isEmpty()) throw new EntityNotFoundException("professor", materialCreationDto.getIdProfessor());

        checkIfExists(id);
        MaterialEntity materialEntity = materialRepository.findById(id).get();
        MaterialsMarkdownEntity materialsMarkdownEntity = materialEntity.getMaterialMarkdown();

//        List<ContentEntity> contentEntities = contentService.findByProfessorId(materialCreationDto.getIdProfessor());
//
//        MarkdownToHtmlParserService markdownParserService = new MarkdownToHtmlParserServiceImpl("neuroapi", "professor" + materialCreationDto.getIdProfessor(), contentEntities);
//        String html = markdownParserService.parse(materialCreationDto.getMarkdownText());

        materialsMarkdownEntity.setMarkdownText(materialCreationDto.getMarkdownText());
        materialsMarkdownEntity.setHtml(materialCreationDto.getHtml());

        materialsMarkdownRepository.save(materialsMarkdownEntity);

        materialEntity = MaterialEntity.builder()
                .course(courseEntityOptional.get())
                .professor(professorEntityOptional.get())
                .title(materialCreationDto.getTitle())
                .materialMarkdown(materialsMarkdownEntity)
                .build();

        materialEntity.setId(id);
        materialRepository.save(materialEntity);
    }

    @Override
    public Optional<MaterialDto> findByTitle(String title) throws UserNotFoundException {
        MaterialEntity materialEntity = Optional.ofNullable(materialRepository.findByTitle(title))
                .orElseThrow(() -> new EntityNotFoundException("Material", title));

        String html = materialEntity.getMaterialMarkdown().getHtml();
        String markdown = materialEntity.getMaterialMarkdown().getMarkdownText();

        return Optional.of(MaterialMapper.toDto(materialEntity, markdown, html));
    }
    public void checkIfExists(Long id) {
        if (materialRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Material", id);
        }
    }

    @Override
    public List<MaterialDto> findAllByCourseId(Long id) {
        CourseEntity courseEntity = Optional.of(courseRepository.findById(id)).get()
                .orElseThrow(() -> new EntityNotFoundException("Course",id));


        List<MaterialDto> materialDtos = courseEntity.getMaterials()
                .stream()
                .map(materialEntity -> MaterialDto.builder()
                        .id(materialEntity.getId())
                        .title(materialEntity.getTitle())
                        .html(materialEntity.getMaterialMarkdown().getHtml())
                        .build())
                .collect(Collectors.toList());

        return materialDtos;
    }

    @Override
    public List<MaterialDto> findAllByTeacherId(Long id) {
        ProfessorEntity professorEntity = Optional.of(professorRepository.findById(id)).get()
                .orElseThrow(() -> new EntityNotFoundException("Professor",id));;

        List<MaterialDto> materialDtos = professorRepository.findById(id).get().getMaterials()
                .stream()
                .map(materialEntity -> MaterialDto.builder()
                        .id(materialEntity.getId())
                        .title(materialEntity.getTitle())
                        .html(materialEntity.getMaterialMarkdown().getHtml())
                        .build())
                .collect(Collectors.toList());

        return materialDtos;
    }

    @Override
    public List<MaterialDto> findByMarkdownId(Long id) {
        MaterialsMarkdownEntity materialsMarkdownEntity = Optional.of(materialsMarkdownRepository.findById(id)).get()
                .orElseThrow(() -> new EntityNotFoundException("Markdown",id));

        List<MaterialDto> materialDtos = materialsMarkdownEntity.getMaterials()
                .stream()
                .map(materialEntity -> MaterialDto.builder()
                        .id(materialEntity.getId())
                        .title(materialEntity.getTitle())
                        .html(materialsMarkdownEntity.getHtml())
                        .build())
                .collect(Collectors.toList());

        return materialDtos;
    }
}

