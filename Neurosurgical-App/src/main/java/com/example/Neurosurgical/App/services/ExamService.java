package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.InvalidDateException;
import com.example.Neurosurgical.App.models.dtos.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExamService {
    void createExam(ExamCreationDto examCreationDto) throws InvalidDateException;

    ExamDto findByCode(String code);

    ExamDto findByCodeForStudent(String code, Long idStudent);

    List<ExamSummariseDto> findByIdStudent(Long idStudent);

    List<ExamSummariseDto> findByIdCourse(Long idCourse);

    void evaluateExam(ExamDto examDto, Long idStudent);

    void evaluateLongResponseQuestion(Long idStudent, Long idQuestion, Double points);

    ExamResultDto viewExamResult(Long idExam, Long idStudent);

    List<ExamStudentPointsDto> getPoints(Long idStudent);

    void activateExam(Long idExam);

    void deactivateExam(Long idExam);

    void deleteExam(Long idExam);

    List<ExamStudentSummariseDto> viewStudentExamSummarise(Long idExam);

    void updateExam(Long idExam, ExamSummariseUpdateDto examSummariseUpdateDto) throws InvalidDateException  ;
}
