package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.advice.exceptions.UserAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.UserNotFoundException;
import com.example.Neurosurgical.App.mappers.MaterialMapper;
import com.example.Neurosurgical.App.models.dtos.MaterialDto;
import com.example.Neurosurgical.App.models.entities.CourseEntity;
import com.example.Neurosurgical.App.models.entities.MaterialEntity;
import com.example.Neurosurgical.App.repositories.CourseRepository;
import com.example.Neurosurgical.App.repositories.MaterialRepository;
import com.example.Neurosurgical.App.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class MaterialServiceImpl implements MaterialService {

    public final MaterialRepository materialRepository;
    public final CourseRepository courseRepository;
    public final ProfessorRepository professorRepository;

    @Autowired
    public MaterialServiceImpl(MaterialRepository materialRepository, CourseRepository courseRepository, ProfessorRepository professorRepository) {
        this.materialRepository = materialRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    public List<MaterialDto> findAll() {
        return materialRepository.findAll()
                .stream()
                .map(MaterialMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMaterial(Long id) {
        materialRepository.deleteById(id);
    }

    @Override
    public Optional<MaterialDto> findById(Long id) throws UserNotFoundException {
        MaterialDto materialDto = MaterialMapper.toDto(materialRepository.findById(id).get());
        return Optional.of(materialDto);
    }

    @Override
    public void createMaterial(MaterialEntity materialEntity) throws UserAlreadyExistsException {
        materialRepository.save(materialEntity);
    }

    @Override
    public void updateMaterial(Long id, MaterialEntity materialEntity) {
        checkIfExists(id);
        // save() -- when we send an object without id, it adds directly a row in database,
        // but if we send an object with an existing id,
        // it changes the columns already found in the database.
        materialEntity.setId(id);
        materialRepository.save(materialEntity);
    }

    @Override
    public Optional<MaterialDto> findByTitle(String title) throws UserNotFoundException {
        MaterialEntity materialEntity = materialRepository.findByTitle(title);

        if(materialEntity == null) throw new EntityNotFoundException("material", title);

        return Optional.of(MaterialMapper.toDto(materialEntity));
    }
    public void checkIfExists(Long id) {
        if (materialRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Material", id);
        }
    }

    @Override
    public List<MaterialDto> findAllByCourseId(Long id) {
        CourseEntity courseEntity = courseRepository.findById(id).get();
        return courseEntity.getMaterials()
                .stream()
                .map(MaterialMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MaterialDto> findAllByTeacherId(Long id) {
        return professorRepository.findById(id).get().getMaterials()
                .stream()
                .map(MaterialMapper::toDto)
                .collect(Collectors.toList());
    }
}

