package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.mappers.CourseMapper;
import com.example.Neurosurgical.App.mappers.MaterialMapper;
import com.example.Neurosurgical.App.models.dtos.CourseDto;
import com.example.Neurosurgical.App.models.dtos.MaterialCreationDto;
import com.example.Neurosurgical.App.models.dtos.MaterialDto;
import com.example.Neurosurgical.App.models.entities.CourseEntity;
import com.example.Neurosurgical.App.models.entities.MaterialEntity;
import com.example.Neurosurgical.App.models.entities.MaterialsMarkdownEntity;
import com.example.Neurosurgical.App.models.entities.ProfessorEntity;
import com.example.Neurosurgical.App.repositories.CourseRepository;
import com.example.Neurosurgical.App.repositories.MaterialRepository;
import com.example.Neurosurgical.App.repositories.MaterialsMarkdownRepository;
import com.example.Neurosurgical.App.repositories.ProfessorRepository;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class MaterialServiceImpl implements MaterialService {

    public final MaterialRepository materialRepository;
    public final CourseRepository courseRepository;
    public final ProfessorRepository professorRepository;
    public final MaterialsMarkdownRepository materialsMarkdownRepository;

    @Autowired
    public MaterialServiceImpl(MaterialRepository materialRepository, CourseRepository courseRepository, ProfessorRepository professorRepository, MaterialsMarkdownRepository materialsMarkdownRepository) {
        this.materialRepository = materialRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
        this.materialsMarkdownRepository = materialsMarkdownRepository;
    }

    @Override
    public List<MaterialDto> findAll() {
        List<MaterialEntity> materialEntities = materialRepository.findAll();
        List<MaterialsMarkdownEntity> materialsMarkdownEntities = materialsMarkdownRepository.findAll();

        List<MaterialDto> materialDtos = new ArrayList<>();

        for (MaterialEntity materialEntity : materialEntities) {
            for (MaterialsMarkdownEntity materialsMarkdownEntity : materialsMarkdownEntities) {
                if(materialEntity.getMaterialMarkdown().getId().equals(materialsMarkdownEntity.getId())){
                    materialDtos.add(MaterialMapper.toDto(materialEntity,materialsMarkdownEntity.getHtml()));
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
        MaterialsMarkdownEntity materialsMarkdownEntity = materialsMarkdownRepository.findByMaterialId(id);

        MaterialDto materialDto = MaterialMapper.toDto(materialEntity,materialsMarkdownEntity.getHtml());

        return Optional.of(materialDto);
    }

    @Override
    public void createMaterial(MaterialCreationDto materialCreationDto) throws UserAlreadyExistsException {
        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(materialCreationDto.getIdCourse());
        Optional<ProfessorEntity> professorEntityOptional = professorRepository.findById(materialCreationDto.getIdProfessor());

        if(courseEntityOptional.isEmpty()) throw new EntityNotFoundException("course", materialCreationDto.getIdCourse());
        if(professorEntityOptional.isEmpty()) throw new EntityNotFoundException("professor", materialCreationDto.getIdProfessor());


        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String html = renderer.render(parser.parse(materialCreationDto.getMarkdownText()));

        // de la front trebuie sa primim un markdown cu toate escape-urile necesare

        MaterialsMarkdownEntity materialsMarkdownEntity = MaterialsMarkdownEntity.builder()
                .markdownText(materialCreationDto.getMarkdownText())
                .html(html)
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

//    @Override
//    public void updateMaterial(Long id, MaterialCreationDto materialCreationDto) throws UserNotFoundException {
//        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(materialCreationDto.getIdCourse());
//        Optional<ProfessorEntity> professorEntityOptional = professorRepository.findById(materialCreationDto.getIdProfessor());
//
//        if(courseEntityOptional.isEmpty()) throw new EntityNotFoundException("course", materialCreationDto.getIdCourse());
//        if(professorEntityOptional.isEmpty()) throw new EntityNotFoundException("professor", materialCreationDto.getIdProfessor());
//
//        checkIfExists(id);
//        MaterialEntity materialEntity = MaterialEntity.builder()
//                .course(courseEntityOptional.get())
//                .professor(professorEntityOptional.get())
//                .title(materialCreationDto.getTitle())
//                .link(materialCreationDto.getLink())
//                .build();
//
//        materialEntity.setId(id);
//        materialRepository.save(materialEntity);
//    }
//
//    @Override
//    public Optional<MaterialDto> findByTitle(String title) throws UserNotFoundException {
//        MaterialEntity materialEntity = Optional.ofNullable(materialRepository.findByTitle(title))
//                .orElseThrow(() -> new EntityNotFoundException("Material", title));
//
//        return Optional.of(MaterialMapper.toDto(materialEntity));
//    }
    public void checkIfExists(Long id) {
        if (materialRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Material", id);
        }
    }
//
//    @Override
//    public List<MaterialDto> findAllByCourseId(Long id) {
//        CourseEntity courseEntity = Optional.of(courseRepository.findById(id)).get()
//                .orElseThrow(() -> new EntityNotFoundException("Course",id));
//        return courseEntity.getMaterials()
//                .stream()
//                .map(MaterialMapper::toDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<MaterialDto> findAllByTeacherId(Long id) {
//        ProfessorEntity professorEntity = Optional.of(professorRepository.findById(id)).get()
//                .orElseThrow(() -> new EntityNotFoundException("Professor",id));
//        return professorRepository.findById(id).get().getMaterials()
//                .stream()
//                .map(MaterialMapper::toDto)
//                .collect(Collectors.toList());
//    }
}

