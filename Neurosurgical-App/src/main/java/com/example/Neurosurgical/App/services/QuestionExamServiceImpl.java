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
import jakarta.transaction.Transactional;
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
    final private ExamRepository examRepository;
    final private ProfessorRepository  professorRepository;
    final private CourseRepository courseRepository;

    @Autowired
    public QuestionExamServiceImpl(QuestionExamRepository questionExamRepository,
                                   AnswerExamRepository answerExamRepository,
                                   ExamRepository examRepository,
                                   ProfessorRepository professorRepository,
                                   CourseRepository courseRepository) {

        this.questionExamRepository = questionExamRepository;
        this.answerExamRepository = answerExamRepository;
        this.examRepository = examRepository;
        this.professorRepository = professorRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<QuestionExamDto> findAll() {
        List<QuestionExamDto> questionExamDtos ;

        List<QuestionExamEntity> questionExamEntities = questionExamRepository.findAll();
        if (questionExamEntities.size() > 0){
            questionExamDtos = questionExamEntities.stream()
                    .map(QuestionExamMapper::toDto)
                    .collect(Collectors.toList());
        }
        else{
            throw new EntityNotFoundException("No Exam questions found");
        }

        return questionExamDtos;
    }

    @Override
    public void createQuestionExam(QuestionExamCreationDto questionExamDto, Long idExam) throws EntityNotFoundException {
        if(!this.examRepository.existsById(idExam)){
            throw new EntityNotFoundException("Exam with id " + idExam + " not found");
        }

        QuestionExamEntity questionExamEntity = QuestionExamMapper.fromCreationDto(questionExamDto);

        ExamEntity examForQuestion = this.examRepository.findById(idExam).get();

        questionExamEntity.setExam(examForQuestion);

        Optional<CourseEntity> course = this.courseRepository.findById(questionExamDto.getIdCourse());

        if( course.isEmpty() ){
            throw new EntityNotFoundException("Course with id " + questionExamDto.getIdCourse() + " not found");
        }
        questionExamEntity.setCourse(course.get());

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
    public void updateQuestionExam(QuestionExamDto questionExamDto, Long idQuestion) throws EntityNotFoundException {

        QuestionExamEntity questionExamEntity = questionExamRepository.findById(idQuestion)
                .orElseThrow(() -> new EntityNotFoundException("Question with ID " , idQuestion.toString()));



        this.answerExamRepository.deleteAll(this.answerExamRepository.findByIdQuestion(idQuestion));

        questionExamEntity.setQuestionText(questionExamDto.getQuestionText());

        questionExamEntity.setProfessor(this.professorRepository.findById(questionExamDto.getIdProfessor()).get());
        //it should also be set the exam, and the course but the professor can't change a question from an exam/course to another

        questionExamEntity.setPoints(questionExamDto.getPoints());

        List<AnswerExamEntity> updatedAnswers = new ArrayList<>();
//        for(AnswerExamEntity answerExamEntity : questionExamEntity.getAnswersQuestion()){
//            answerExamEntity.setAnswerText("text-text");
//        }

        for (AnswerExamDto answerExamDto : questionExamDto.getAnswersQuestion()) {
            AnswerExamEntity answer = AnswerExamMapper.fromDto(answerExamDto, questionExamEntity);
            updatedAnswers.add(answer);
        }

        questionExamEntity.setAnswersQuestion(updatedAnswers);

        this.questionExamRepository.save(questionExamEntity);

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
