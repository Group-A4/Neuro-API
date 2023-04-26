package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.mappers.AnswerQuizzMapper;
import com.example.Neurosurgical.App.mappers.QuestionQuizzMapper;
import com.example.Neurosurgical.App.models.dtos.QuestionQuizzDto;
import com.example.Neurosurgical.App.models.entities.CourseEntity;
import com.example.Neurosurgical.App.models.entities.ProfessorEntity;
import com.example.Neurosurgical.App.models.entities.QuestionQuizzEntity;
import com.example.Neurosurgical.App.repositories.CourseRepository;
import com.example.Neurosurgical.App.repositories.ProfessorRepository;
import com.example.Neurosurgical.App.repositories.QuestionQuizzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionQuizzServiceImpl implements QuestionQuizzService {
    private final QuestionQuizzRepository questionQuizzRepository;
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;

    @Autowired
    public QuestionQuizzServiceImpl(QuestionQuizzRepository questionQuizzRepository, CourseRepository courseRepository, ProfessorRepository professorRepository) {
        this.questionQuizzRepository = questionQuizzRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    public Optional<QuestionQuizzDto> findById(Long id) throws EntityNotFoundException {
        Optional<QuestionQuizzEntity> list
                = questionQuizzRepository.findById(id);

        if(list.isEmpty())
            throw new EntityNotFoundException("QuestionQuizz", id.toString());

        return Optional.of(QuestionQuizzMapper.toDto(list.get()));
    }

    @Override
    public List<QuestionQuizzDto> findAll() {
        return questionQuizzRepository.findAll()
                .stream()
                .map(QuestionQuizzMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<List<QuestionQuizzDto>> findByIdProfessorAndIdCourse(Long idProfessor, Long idCourse) throws EntityNotFoundException {
        Optional<List<QuestionQuizzEntity>> list
                = questionQuizzRepository.findByIdProfessorAndIdCourse(idProfessor, idCourse);

        if(list.isEmpty())
            throw new EntityNotFoundException("QuestionQuizz", idProfessor.toString());

        return Optional.of(
                list.get()
                        .stream()
                        .map(QuestionQuizzMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<List<QuestionQuizzDto>> findByIdProfessor(Long id) {
        Optional<List<QuestionQuizzEntity>> list
                = questionQuizzRepository.findByIdProfessor(id);
        if(list.isEmpty())
            throw new EntityNotFoundException("QuestionQuizz", id.toString());

        return Optional.of(
                list.get()
                        .stream()
                        .map(QuestionQuizzMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<List<QuestionQuizzDto>> findByIdCourse(Long id) {
        Optional<List<QuestionQuizzEntity>> list
                = questionQuizzRepository.findByIdCourse(id);
        if(list.isEmpty())
            throw new EntityNotFoundException("QuestionQuizz", id.toString());

        return Optional.of(
                list.get()
                        .stream()
                        .map(QuestionQuizzMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void createQuestionQuizz(QuestionQuizzDto questionQuizzDto) {
        long courseId = questionQuizzDto.getIdCourse();
        long professorId = questionQuizzDto.getIdProfessor();

        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(courseId);
        Optional<ProfessorEntity> professorEntityOptional = professorRepository.findById(professorId);

        if(courseEntityOptional.isEmpty()) throw new EntityNotFoundException("course", courseId);
        if(professorEntityOptional.isEmpty()) throw new EntityNotFoundException("professor", professorId);

        QuestionQuizzEntity questionQuizzEntity = QuestionQuizzMapper.fromDto(questionQuizzDto);

        questionQuizzEntity.setCourse(courseEntityOptional.get());
        questionQuizzEntity.setProfessor(professorEntityOptional.get());

        questionQuizzRepository.save(questionQuizzEntity);
    }

    @Override
    public void deleteQuestionQuizzById(Long id) {
        checkIfExistsById(id);
        questionQuizzRepository.deleteById(id);
    }

    @Override
    public void updateQuestionQuizz(Long id, QuestionQuizzDto questionQuizzDto) {
        checkIfExistsById(id);
        long courseId = questionQuizzDto.getIdCourse();
        long professorId = questionQuizzDto.getIdProfessor();
        String questionText = questionQuizzDto.getQuestionText();

        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(courseId);
        Optional<ProfessorEntity> professorEntityOptional = professorRepository.findById(professorId);

        if(courseEntityOptional.isEmpty()) throw new EntityNotFoundException("course", courseId);
        if(professorEntityOptional.isEmpty()) throw new EntityNotFoundException("professor", professorId);

        QuestionQuizzEntity questionQuizz
                = QuestionQuizzEntity.builder()
                        .course(courseEntityOptional.get())
                        .professor(professorEntityOptional.get())
                        .questionText(questionText)
                        .answersQuestion(questionQuizzDto.getAnswersQuestion()
                                .stream().map(AnswerQuizzMapper::fromDto).collect(Collectors.toList()))
                        .build();
        questionQuizz.setId(id);

        questionQuizzRepository.save(questionQuizz);
    }

    @Override
    public void updateQuestionQuizzText(Long id, String questionText) {
        checkIfExistsById(id);

        QuestionQuizzEntity questionQuizzEntity = questionQuizzRepository.findById(id).get();
        questionQuizzEntity.setQuestionText(questionText);

        questionQuizzRepository.save(questionQuizzEntity);
    }

    public void checkIfExistsById(Long id) throws EntityNotFoundException {
        if(!questionQuizzRepository.existsById(id))
            throw new EntityNotFoundException("QuestionQuizz", id.toString());
    }
}
