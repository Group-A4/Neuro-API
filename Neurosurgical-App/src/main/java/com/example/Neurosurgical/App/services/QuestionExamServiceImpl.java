package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.mappers.AnswerExamMapper;
import com.example.Neurosurgical.App.mappers.CorrectAnswerExamMapper;
import com.example.Neurosurgical.App.mappers.QuestionExamMapper;
import com.example.Neurosurgical.App.models.dtos.AnswerExamDto;
import com.example.Neurosurgical.App.models.dtos.QuestionExamCreationDto;
import com.example.Neurosurgical.App.models.dtos.QuestionExamDto;
import com.example.Neurosurgical.App.models.entities.*;
import com.example.Neurosurgical.App.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionExamServiceImpl implements QuestionExamService{
    final private QuestionExamRepository questionExamRepository;
    final private AnswerExamRepository answerExamRepository;
    final private CorrectAnswerExamRepository correctAnswerExamRepository;
    final private ExamRepository examRepository;
    final private ProfessorRepository  professorRepository;

    @Autowired
    public QuestionExamServiceImpl(QuestionExamRepository questionExamRepository,
                                   AnswerExamRepository answerExamRepository,
                                   CorrectAnswerExamRepository correctAnswerExamRepository,
                                   ExamRepository examRepository,
                                   ProfessorRepository professorRepository){

        this.questionExamRepository = questionExamRepository;
        this.answerExamRepository = answerExamRepository;
        this.correctAnswerExamRepository = correctAnswerExamRepository;
        this.examRepository = examRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    public Optional<QuestionExamDto> findById(Long id) throws EntityNotFoundException {
        return Optional.empty();
    }

    @Override
    public List<QuestionExamDto> findAll() {
        return questionExamRepository.findAll().stream()
                .map( questionEntity -> QuestionExamMapper.toDto(questionEntity,
                        this.answerExamRepository.findByIdQuestion(questionEntity.getId()),
                        this.correctAnswerExamRepository.findByIdQuestion(questionEntity.getId())) )
                .collect(Collectors.toList());
    }

    @Override
    public void createQuestionExam(QuestionExamCreationDto questionExamDto, Long idExam) throws EntityNotFoundException {
        if(!this.examRepository.existsById(idExam)){
            throw new EntityNotFoundException("Exam with id " + idExam + " not found");
        }

        QuestionExamEntity questionExamEntity = QuestionExamMapper.fromCreationDto(questionExamDto);

        ExamEntity examForQuestion = this.examRepository.findById(idExam).get();

        questionExamEntity.setExam(examForQuestion);
        questionExamEntity.setCourse(examForQuestion.getCourse());

        Optional<ProfessorEntity> professor = professorRepository.findById(questionExamDto.getIdProfessor());

        if( professor.isEmpty() ){
            throw new EntityNotFoundException("Professor with id " + questionExamDto.getIdProfessor() + " not found");
        }

        questionExamEntity.setProfessor(professor.get());

        try{
            this.questionExamRepository.save(questionExamEntity);
        }
        catch (Exception e){
            throw new EntityAlreadyExistsException("Question already exists or invalid input");
        }

    }

    @Override
    public void updateQuestionExam(QuestionExamDto questionExamCreationDto, Long idQuestion) throws EntityNotFoundException {
        Optional<QuestionExamEntity> questionExamEntity = this.questionExamRepository.findById(idQuestion);

        if (questionExamEntity.isEmpty()){
            throw new EntityNotFoundException("Question with id " + idQuestion + " not found");
        }

        //firstly, we delete all existing questions
        List<AnswerExamEntity> beforeUpdateAnswers = this.answerExamRepository.findByIdQuestion(idQuestion);
        this.answerExamRepository.deleteAll(beforeUpdateAnswers);

        questionExamEntity.get().setQuestionText(questionExamCreationDto.getQuestionText());
        List<AnswerExamEntity> listAnswers = new ArrayList<>();
        List<CorrectAnswerExamEntity> listCorrectAnswers = new ArrayList<>();

        for (AnswerExamDto answerExamDto : questionExamCreationDto.getAnswersQuestion() ){
            AnswerExamEntity answer = AnswerExamMapper.fromDto(answerExamDto,questionExamEntity.get());
            listAnswers.add(answer);
            if(answerExamDto.isCorrect()){
                listCorrectAnswers.add(CorrectAnswerExamMapper.fromAnswerExamEntity(answer,questionExamEntity.get()));
            }
        }

        questionExamEntity.get().setAnswersQuestion(listAnswers);
        questionExamEntity.get().setCorrectAnswersQuestion(listCorrectAnswers);
        this.questionExamRepository.save(questionExamEntity.get());
    }

    @Override
    public void deleteQuestionExam(Long idQuestion) throws EntityNotFoundException {

        Optional<QuestionExamEntity> questionExamEntity = this.questionExamRepository.findById(idQuestion);

        if(questionExamEntity.isEmpty()){
            throw new EntityNotFoundException("Question with id " + idQuestion + " not found");
        }

        this.questionExamRepository.delete(questionExamEntity.get());

    }

}
