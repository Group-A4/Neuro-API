package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.mappers.ExamMapper;
import com.example.Neurosurgical.App.mappers.QuestionExamMapper;
import com.example.Neurosurgical.App.models.dtos.*;
import com.example.Neurosurgical.App.models.entities.*;
import com.example.Neurosurgical.App.repositories.CourseRepository;
import com.example.Neurosurgical.App.repositories.ExamRepository;
import com.example.Neurosurgical.App.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ExamServiceImpl implements ExamService {

    final private ExamRepository examRepository;
    final private CourseRepository courseRepository;

    final private ProfessorRepository professorRepository;


    @Autowired
    public ExamServiceImpl(ExamRepository examRepository,
                           CourseRepository courseRepository,
                           ProfessorRepository professorRepository) {

        this.examRepository = examRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
    }


    @Override
    public void createExam(ExamCreationDto examCreationDto) {
        CourseEntity courseEntity = this.courseRepository.findById(examCreationDto.getIdCourse())
                .orElseThrow(() -> new EntityNotFoundException("Course", examCreationDto.getIdCourse()));

        ProfessorEntity professorEntity = this.professorRepository.findById(examCreationDto.getIdProfessor())
                .orElseThrow(() -> new EntityNotFoundException("Professor", examCreationDto.getIdProfessor()));


        ExamEntity examEntity = ExamMapper.fromCreationDto(examCreationDto);

        examEntity.setCourse(courseEntity);
        examEntity.setProfessor(professorEntity);
        examEntity.setCode(this.generateCode());


        List<QuestionExamEntity> questionsExam = new ArrayList<>();
        for(QuestionMultipleChoiceExamCreationDto questionMultipleChoiceExamCreationDto : examCreationDto.getQuestionsMultipleChoice()){
            QuestionExamEntity questionExamEntity = QuestionExamMapper.fromCreationDto(questionMultipleChoiceExamCreationDto);
            questionExamEntity.setProfessor(professorEntity);
            questionExamEntity.setExam(examEntity);
            questionsExam.add(questionExamEntity);
        }

        for(QuestionLongResponseExamCreationDto questionLongResponseExamCreationDto : examCreationDto.getQuestionsLongResponse()){
            QuestionExamEntity questionExamEntity = QuestionExamMapper.fromLongResponseDto(questionLongResponseExamCreationDto);
            questionExamEntity.setProfessor(professorEntity);
            questionExamEntity.setExam(examEntity);
            QuestionLongResponseExamEntity questionLongResponseExamEntity =
                    QuestionLongResponseExamEntity.builder()
                                    .expectedResponse(questionLongResponseExamCreationDto.getExpectedResponse())
                                    .question(questionExamEntity)
                                    .build();
            questionExamEntity.setQuestionLongResponseExam(questionLongResponseExamEntity);
            questionsExam.add(questionExamEntity);
        }

        examEntity.setQuestionsExam(questionsExam);

        try {
            this.examRepository.save(examEntity);
        } catch (Exception e){
            throw new EntityAlreadyExistsException("Exam already exists or invalid input");
        }

    }

    @Override
    public ExamDto findByCode(String code) {

        ExamEntity examEntity = this.examRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Exam", code));

        return ExamMapper.toDto(examEntity);

    }

    @Override
    public List<ExamSummariseDto> findByIdStudent(Long idStudent) {
        List<ExamEntity> examEntities = this.examRepository.findByIdStudent(idStudent);

        if (examEntities.isEmpty()){
            throw new EntityNotFoundException("Exams for student ", idStudent);
        }

        return examEntities.stream().map(ExamMapper::toSummariseDto).toList();

    }

    @Override
    public List<ExamSummariseDto> findByIdCourse(Long idCourse) {
        List<ExamEntity> examEntities = this.examRepository.findByIdCourse(idCourse);

        if (examEntities.isEmpty()){
            throw new EntityNotFoundException("Exams for course ", idCourse);
        }

        return examEntities.stream().map(ExamMapper::toSummariseDto).toList();
    }

    private String generateCode() {
        StringBuilder alphabet = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        do{
            for(int i= 0; i < 6; i++) {
                sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
            }
        }while(this.examRepository.findByCode(sb.toString()).isPresent());


        return sb.toString();

    }


}
