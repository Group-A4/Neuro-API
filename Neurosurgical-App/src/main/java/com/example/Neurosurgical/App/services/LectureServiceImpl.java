package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.dtos.LectureCreationDto;
import com.example.Neurosurgical.App.models.entities.CourseEntity;
import com.example.Neurosurgical.App.models.entities.LectureEntity;
import com.example.Neurosurgical.App.models.entities.MaterialEntity;
import com.example.Neurosurgical.App.repositories.CourseRepository;
import com.example.Neurosurgical.App.repositories.LectureRepository;
import com.example.Neurosurgical.App.repositories.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LectureServiceImpl implements LectureService{

    private final LectureRepository lectureRepository;
    private final CourseRepository courseRepository;
    private final MaterialRepository materialRepository;

    @Autowired
    public LectureServiceImpl(LectureRepository lectureRepository, CourseRepository courseRepository, MaterialRepository materialRepository) {
        this.lectureRepository = lectureRepository;
        this.courseRepository = courseRepository;
        this.materialRepository = materialRepository;
    }

    @Override
    public List<LectureEntity> findAll() {
        List<LectureEntity> lectureEntities = Optional.ofNullable(lectureRepository.findAll()).orElse(new ArrayList<>());
        return lectureEntities;
    }

    @Override
    public LectureEntity findById(Long id) {
        LectureEntity lectureEntity = lectureRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Lecture", id));
        return lectureEntity;
    }

    @Override
    public void deleteLecture(Long id) {
        lectureRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Lecture", id));
        lectureRepository.deleteById(id);
    }

    @Override
    public void createLecture(LectureCreationDto lectureCreationDto) {
        CourseEntity courseEntity = courseRepository.findById(lectureCreationDto.getIdCourse()).orElseThrow(() -> new EntityNotFoundException("Course", lectureCreationDto.getIdCourse()));
        LectureEntity lectureEntity = LectureEntity.builder()
                .course(courseEntity)
                .description(lectureCreationDto.getDescription())
                .title(lectureCreationDto.getTitle())
                .build();

        lectureRepository.save(lectureEntity);
    }

    @Override
    public void updateLecture(Long id, LectureCreationDto lectureCreationDto) {
        LectureEntity lectureEntity = lectureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lecture", id));
        CourseEntity courseEntity = courseRepository.findById(lectureCreationDto.getIdCourse())
                .orElseThrow(() -> new EntityNotFoundException("Course", lectureCreationDto.getIdCourse()));

        lectureEntity.setCourse(courseEntity);
        lectureEntity.setDescription(lectureCreationDto.getDescription());
        lectureEntity.setTitle(lectureCreationDto.getTitle());

        lectureRepository.save(lectureEntity);
    }

    @Override
    public LectureEntity findByTitle(String title) {
        LectureEntity lectureEntity = lectureRepository.findByTitle(title).orElseThrow(() -> new EntityNotFoundException("Lecture", title));
        return lectureEntity;
    }

    @Override
    public List<LectureEntity> findAllByCourseId(Long id) {
        courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course", id));
        List<LectureEntity> lectureEntities = Optional.ofNullable(lectureRepository.findAllByCourseId(id)).orElse(new ArrayList<>());
        return lectureEntities;
    }

    @Override
    public LectureEntity findByMaterialId(Long id) {
        MaterialEntity materialEntity = materialRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Material",id));
        LectureEntity lectureEntity = materialEntity.getLecture();
        return lectureEntity;
    }
}
