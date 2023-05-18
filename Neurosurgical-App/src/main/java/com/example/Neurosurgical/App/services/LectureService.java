package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.models.dtos.LectureCreationDto;
import com.example.Neurosurgical.App.models.entities.LectureEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LectureService {
    List<LectureEntity> findAll();
    void deleteLecture(Long id);
    LectureEntity findById(Long id);
    void createLecture(LectureCreationDto lectureCreationDto);
    void updateLecture(Long id, LectureCreationDto lectureCreationDto);
    LectureEntity findByTitle(String title);
    List<LectureEntity> findAllByCourseId(Long id);
    LectureEntity findByMaterialId(Long id);

}
