package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.models.dtos.ExamCreationDto;
import com.example.Neurosurgical.App.models.dtos.ExamDto;
import com.example.Neurosurgical.App.models.dtos.ExamSummariseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExamService {
    void createExam(ExamCreationDto examCreationDto);

    ExamDto findByCode(String code);

    List<ExamSummariseDto> findByIdStudent(Long idStudent);

    List<ExamSummariseDto> findByIdCourse(Long idCourse);
}
