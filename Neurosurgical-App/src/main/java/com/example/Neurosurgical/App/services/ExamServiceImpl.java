package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.mappers.ExamMapper;
import com.example.Neurosurgical.App.mappers.ExamQuestionMapper;
import com.example.Neurosurgical.App.models.dtos.ExamDto;
import com.example.Neurosurgical.App.models.dtos.ExamQuestionDto;
import com.example.Neurosurgical.App.models.entities.ExamEntity;
import com.example.Neurosurgical.App.models.entities.ExamQuestionEntity;
import com.example.Neurosurgical.App.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExamServiceImpl implements ExamService {
    final private ExamRepository examRepository;
    final private ExamQuestionRepository examQuestionRepository;
    final private ExamAnswerRepository examAnswerRepository;
    final private CorrectExamAnswerRepository correctExamAnswerRepository;

    @Autowired
    public ExamServiceImpl(ExamRepository examRepository, ExamQuestionRepository examQuestionRepository,
                           ExamAnswerRepository examAnswerRepository,
                           CorrectExamAnswerRepository correctExamAnswerRepository) {
        this.examRepository = examRepository;
        this.examQuestionRepository = examQuestionRepository;
        this.examAnswerRepository = examAnswerRepository;
        this.correctExamAnswerRepository = correctExamAnswerRepository;
    }

    @Override
    public Optional<List<ExamQuestionDto>> findById(Long id) throws EntityNotFoundException {
        //return the exam questions with the specified id
        List<ExamQuestionDto> exam = new ArrayList<>();

        Optional<ArrayList<Long>> questionsIds = this.examQuestionRepository.findByExamId(id);
        if(questionsIds.isEmpty()){
            throw new EntityNotFoundException("Question", id);
        }

        int size = questionsIds.orElse(new ArrayList<>()).size();
        for(int i=0; i<size; i++){
            Optional<ExamQuestionEntity> opt = this.examQuestionRepository.findByQuestionId(questionsIds.get().get(i));

            if(opt.isEmpty()){
                throw new EntityNotFoundException("Question", id);
            }

            exam.add(ExamQuestionMapper.toDto(
                    opt.get(),
                    this.examAnswerRepository.findByIdQuestion(opt.get().getId()),
                    this.correctExamAnswerRepository.findByIdQuestion(opt.get().getId())
                    )
            );
        }
        return Optional.of(exam);
    }

    @Override
    public Optional<List<ExamDto>> findByCourseId(Long id) throws EntityNotFoundException {
        List<ExamDto> exams = new ArrayList<>();

        Optional<ArrayList<Long>> examsIds = this.examRepository.findByIdCourse(id);
        if (examsIds.isEmpty())
            throw new EntityNotFoundException("Exam", id);

        int size = examsIds.orElse(new ArrayList<>()).size();
        for(int i=0; i<size; i++){
            Optional<ExamEntity> opt = this.examRepository.findById(examsIds.get().get(i));

            if(opt.isEmpty()){
                throw new EntityNotFoundException("Exam", id);
            }

            exams.add(ExamMapper.toDto(
                    opt.get()
            ));
        }

        return Optional.of(exams);
    }

    @Override
    public void deleteExamById(Long id) throws EntityNotFoundException {
        checkIfExistsById(id);
        examRepository.deleteById(id);
    }
    public void checkIfExistsById(Long id) throws EntityNotFoundException {
        if(!examRepository.existsById(id))
            throw new EntityNotFoundException("Exam", id.toString());
    }
}
