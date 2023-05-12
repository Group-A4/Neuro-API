package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.mappers.QuestionExamMapper;
import com.example.Neurosurgical.App.models.dtos.AnswerExamDto;
import com.example.Neurosurgical.App.models.dtos.QuestionExamDto;
import com.example.Neurosurgical.App.models.entities.*;
import com.example.Neurosurgical.App.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionExamServiceImpl implements QuestionExamService {
    final private QuestionExamRepository questionExamRepository;
    final private AnswerExamRepository answerExamRepository;
    final private CorrectAnswerExamRepository correctAnswerExamRepository;
    final private CourseRepository courseRepository;
    final private ProfessorRepository professorRepository;

    @Autowired
    public QuestionExamServiceImpl(QuestionExamRepository questionExamRepository,
                                   AnswerExamRepository answerExamRepository,
                                   CorrectAnswerExamRepository correctAnswerExamRepository,
                                   CourseRepository courseRepository,
                                   ProfessorRepository professorRepository) {
        this.questionExamRepository = questionExamRepository;
        this.answerExamRepository = answerExamRepository;
        this.correctAnswerExamRepository = correctAnswerExamRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    public Optional<QuestionExamDto> findById(Long id) throws EntityNotFoundException {
        Optional<QuestionExamEntity> list = questionExamRepository.findById(id);

        if(list.isEmpty()){
            throw new EntityNotFoundException("ExamQuestion", id.toString());
        }

        return Optional.of(QuestionExamMapper.toDto(list.get(),
                this.answerExamRepository.findByIdQuestion(id),
                this.correctAnswerExamRepository.findByIdQuestion(id))
        );
    }

    @Override
    public List<QuestionExamDto> findAll() {
        return questionExamRepository.findAll().stream()
                .map( questionEntity -> QuestionExamMapper.toDto(questionEntity,
                        this.answerExamRepository.findByIdQuestion(questionEntity.getId()),
                        this.correctAnswerExamRepository.findByIdQuestion(questionEntity.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<List<QuestionExamDto>> findByIdProfessorAndIdCourse(Long idProfessor, Long idCourse) throws EntityNotFoundException {
        Optional<List<QuestionExamEntity>> list = questionExamRepository.findByIdProfessorAndIdCourse(idProfessor, idCourse);

        if(list.isEmpty()){
            throw new EntityNotFoundException("ExamQuestion", idProfessor.toString());
        }

        return Optional.of(list.get()
                .stream()
                .map( questionEntity -> QuestionExamMapper.toDto(questionEntity,
                        this.answerExamRepository.findByIdQuestion(questionEntity.getId()),
                        this.correctAnswerExamRepository.findByIdQuestion(questionEntity.getId())))
                .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<List<QuestionExamDto>> findByIdProfessor(Long id) throws EntityNotFoundException {
        Optional<List<QuestionExamEntity>> list = questionExamRepository.findByIdProfessor(id);

        if(list.isEmpty())
            throw new EntityNotFoundException("ExamQuestion", id.toString());

        return Optional.of(list.get()
                .stream()
                .map( questionEntity -> QuestionExamMapper.toDto(questionEntity,
                        this.answerExamRepository.findByIdQuestion(questionEntity.getId()),
                        this.correctAnswerExamRepository.findByIdQuestion(questionEntity.getId())))
                .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<List<QuestionExamDto>> findByIdCourse(Long id) throws EntityNotFoundException {
        Optional<List<QuestionExamEntity>> list = questionExamRepository.findByIdCourse(id);

        if(list.isEmpty()){
            throw new EntityNotFoundException("ExamQuestion", id.toString());
        }

        return Optional.of(list.get()
                .stream()
                .map( questionEntity -> QuestionExamMapper.toDto(questionEntity,
                        this.answerExamRepository.findByIdQuestion(questionEntity.getId()),
                        this.correctAnswerExamRepository.findByIdQuestion(questionEntity.getId())))
                .collect(Collectors.toList())
        );
    }

    @Override
    public void createExamQuestion(QuestionExamDto questionExamDto) throws EntityNotFoundException {
        long courseId = questionExamDto.getIdCourse();
        long professorId = questionExamDto.getIdProfessor();

        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(courseId);
        Optional<ProfessorEntity> professorEntityOptional = professorRepository.findById(professorId);

        if(courseEntityOptional.isEmpty())
            throw new EntityNotFoundException("Course", courseId);
        if(professorEntityOptional.isEmpty())
            throw new EntityNotFoundException("Professor", professorId);

        QuestionExamEntity questionExamEntity = QuestionExamMapper.fromDto(questionExamDto);

        questionExamEntity.setCourse(courseEntityOptional.get());
        questionExamEntity.setProfessor(professorEntityOptional.get());

        questionExamRepository.save(questionExamEntity);
    }

    @Override
    public void deleteExamQuestionById(Long id) throws EntityNotFoundException {
        checkIfExistsById(id);
        questionExamRepository.deleteById(id);
    }

    @Override
    public void updateExamQuestionText(Long id, String questionText) throws EntityNotFoundException {
        checkIfExistsById(id);

        QuestionExamEntity questionExamEntity = questionExamRepository.findById(id).get();
        questionExamEntity.setQuestionText(questionText);

        questionExamRepository.save(questionExamEntity);
    }

    @Override
    public void updateExamQuestion(Long id, QuestionExamDto questionExamDto) throws EntityNotFoundException {
        long courseId = questionExamDto.getIdCourse();
        long professorId = questionExamDto.getIdProfessor();

        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(courseId);
        Optional<ProfessorEntity> professorEntityOptional = professorRepository.findById(professorId);

        if (courseEntityOptional.isEmpty()) throw new EntityNotFoundException("Course", courseId);
        if (professorEntityOptional.isEmpty()) throw new EntityNotFoundException("Professor", professorId);

        Optional<QuestionExamEntity> examQuestionEntityOptional = this.questionExamRepository.findById(id);

        if(examQuestionEntityOptional.isEmpty())
            throw new EntityNotFoundException("Question", id);

        //find the answers
        List<AnswerExamEntity> examAnswersEntity = this.answerExamRepository.findByIdQuestion(id);
        List<CorrectAnswerExamEntity> correctExamAnswersEntity = this.correctAnswerExamRepository.findByIdQuestion(id);

        examQuestionEntityOptional.get().setCourse(courseEntityOptional.get());
        examQuestionEntityOptional.get().setProfessor(professorEntityOptional.get());
        examQuestionEntityOptional.get().setQuestionText(questionExamDto.getQuestionText());
        examQuestionEntityOptional.get().setId(id);

        //save the updated question
        questionExamRepository.save(examQuestionEntityOptional.get());

        //delete the existing answers
        this.answerExamRepository.deleteAll(examAnswersEntity);
        this.correctAnswerExamRepository.deleteAll(correctExamAnswersEntity);

        //create new answers
        for (AnswerExamDto answer : questionExamDto.getAnswersQuestions()){
            AnswerExamEntity answerExamEntity = AnswerExamEntity.builder()
                    .answerText(answer.getAnswerText())
                    .question(examQuestionEntityOptional.get())
                    .build();

            this.answerExamRepository.save(answerExamEntity);

            if(answer.isCorrect()){
                CorrectAnswerExamEntity correctAnswerExamEntity = CorrectAnswerExamEntity.builder()
                        .answer(answerExamEntity)
                        .question(examQuestionEntityOptional.get())
                        .build();

                this.correctAnswerExamRepository.save(correctAnswerExamEntity);
            }
        }
    }

    public void checkIfExistsById(Long id) throws EntityNotFoundException {
        if(!questionExamRepository.existsById(id))
            throw new EntityNotFoundException("ExamQuestion", id.toString());
    }
}
