package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.InvalidDateException;
import com.example.Neurosurgical.App.models.dtos.ExamCreationDto;
import com.example.Neurosurgical.App.models.dtos.ExamDto;
import com.example.Neurosurgical.App.models.dtos.ExamResultDto;
import com.example.Neurosurgical.App.models.dtos.ExamSummariseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExamService {
    void createExam(ExamCreationDto examCreationDto) throws InvalidDateException;

    ExamDto findByCode(String code);

    List<ExamSummariseDto> findByIdStudent(Long idStudent);

    List<ExamSummariseDto> findByIdCourse(Long idCourse);

    void evaluateExam(ExamDto examDto, Long idStudent);

    void evaluateLongResponseQuestion(Long idStudent, Long idQuestion, Double points);

    ExamResultDto viewExamResult(Long idExam, Long idStudent);
}
