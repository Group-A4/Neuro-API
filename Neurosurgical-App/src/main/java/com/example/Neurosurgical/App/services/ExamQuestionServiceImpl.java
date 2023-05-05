package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.mappers.ExamQuestionMapper;
import com.example.Neurosurgical.App.models.dtos.ExamAnswerDto;
import com.example.Neurosurgical.App.models.dtos.ExamQuestionDto;
import com.example.Neurosurgical.App.models.entities.*;
import com.example.Neurosurgical.App.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExamQuestionServiceImpl implements ExamQuestionService {
    final private ExamQuestionRepository examQuestionRepository;
    final private ExamAnswerRepository examAnswerRepository;
    final private CorrectExamAnswerRepository correctExamAnswerRepository;
    final private CourseRepository courseRepository;
    final private ProfessorRepository professorRepository;

    @Autowired
    public ExamQuestionServiceImpl(ExamQuestionRepository examQuestionRepository,
                                   ExamAnswerRepository examAnswerRepository,
                                   CorrectExamAnswerRepository correctExamAnswerRepository,
                                   CourseRepository courseRepository,
                                   ProfessorRepository professorRepository) {
        this.examQuestionRepository = examQuestionRepository;
        this.examAnswerRepository = examAnswerRepository;
        this.correctExamAnswerRepository = correctExamAnswerRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    public Optional<ExamQuestionDto> findById(Long id) throws EntityNotFoundException {
        Optional<ExamQuestionEntity> list = examQuestionRepository.findById(id);

        if(list.isEmpty()){
            throw new EntityNotFoundException("ExamQuestion", id.toString());
        }

        return Optional.of(ExamQuestionMapper.toDto(list.get(),
                this.examAnswerRepository.findByIdQuestion(id),
                this.correctExamAnswerRepository.findByIdQuestion(id))
        );
    }

    @Override
    public List<ExamQuestionDto> findAll() {
        return examQuestionRepository.findAll().stream()
                .map( questionEntity -> ExamQuestionMapper.toDto(questionEntity,
                        this.examAnswerRepository.findByIdQuestion(questionEntity.getId()),
                        this.correctExamAnswerRepository.findByIdQuestion(questionEntity.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<List<ExamQuestionDto>> findByIdProfessorAndIdCourse(Long idProfessor, Long idCourse) throws EntityNotFoundException {
        Optional<List<ExamQuestionEntity>> list = examQuestionRepository.findByIdProfessorAndIdCourse(idProfessor, idCourse);

        if(list.isEmpty()){
            throw new EntityNotFoundException("ExamQuestion", idProfessor.toString());
        }

        return Optional.of(list.get()
                .stream()
                .map( questionEntity -> ExamQuestionMapper.toDto(questionEntity,
                        this.examAnswerRepository.findByIdQuestion(questionEntity.getId()),
                        this.correctExamAnswerRepository.findByIdQuestion(questionEntity.getId())))
                .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<List<ExamQuestionDto>> findByIdProfessor(Long id) throws EntityNotFoundException {
        Optional<List<ExamQuestionEntity>> list = examQuestionRepository.findByIdProfessor(id);

        if(list.isEmpty())
            throw new EntityNotFoundException("ExamQuestion", id.toString());

        return Optional.of(list.get()
                .stream()
                .map( questionEntity -> ExamQuestionMapper.toDto(questionEntity,
                        this.examAnswerRepository.findByIdQuestion(questionEntity.getId()),
                        this.correctExamAnswerRepository.findByIdQuestion(questionEntity.getId())))
                .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<List<ExamQuestionDto>> findByIdCourse(Long id) throws EntityNotFoundException {
        Optional<List<ExamQuestionEntity>> list = examQuestionRepository.findByIdCourse(id);

        if(list.isEmpty()){
            throw new EntityNotFoundException("ExamQuestion", id.toString());
        }

        return Optional.of(list.get()
                .stream()
                .map( questionEntity -> ExamQuestionMapper.toDto(questionEntity,
                        this.examAnswerRepository.findByIdQuestion(questionEntity.getId()),
                        this.correctExamAnswerRepository.findByIdQuestion(questionEntity.getId())))
                .collect(Collectors.toList())
        );
    }

    @Override
    public void createExamQuestion(ExamQuestionDto examQuestionDto) throws EntityNotFoundException {
        long courseId = examQuestionDto.getIdCourse();
        long professorId = examQuestionDto.getIdProfessor();

        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(courseId);
        Optional<ProfessorEntity> professorEntityOptional = professorRepository.findById(professorId);

        if(courseEntityOptional.isEmpty())
            throw new EntityNotFoundException("Course", courseId);
        if(professorEntityOptional.isEmpty())
            throw new EntityNotFoundException("Professor", professorId);

        ExamQuestionEntity examQuestionEntity = ExamQuestionMapper.fromDto(examQuestionDto);

        examQuestionEntity.setCourse(courseEntityOptional.get());
        examQuestionEntity.setProfessor(professorEntityOptional.get());

        examQuestionRepository.save(examQuestionEntity);
    }

    @Override
    public void deleteExamQuestionById(Long id) throws EntityNotFoundException {
        checkIfExistsById(id);
        examQuestionRepository.deleteById(id);
    }

    @Override
    public void updateExamQuestionText(Long id, String questionText) throws EntityNotFoundException {
        checkIfExistsById(id);

        ExamQuestionEntity examQuestionEntity = examQuestionRepository.findById(id).get();
        examQuestionEntity.setQuestionText(questionText);

        examQuestionRepository.save(examQuestionEntity);
    }

    @Override
    public void updateExamQuestion(Long id, ExamQuestionDto examQuestionDto) throws EntityNotFoundException {
        long courseId = examQuestionDto.getIdCourse();
        long professorId = examQuestionDto.getIdProfessor();

        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(courseId);
        Optional<ProfessorEntity> professorEntityOptional = professorRepository.findById(professorId);

        if (courseEntityOptional.isEmpty()) throw new EntityNotFoundException("Course", courseId);
        if (professorEntityOptional.isEmpty()) throw new EntityNotFoundException("Professor", professorId);

        Optional<ExamQuestionEntity> examQuestionEntityOptional = this.examQuestionRepository.findById(id);

        if(examQuestionEntityOptional.isEmpty())
            throw new EntityNotFoundException("Question", id);

        //find the answers
        List<ExamAnswerEntity> examAnswersEntity = this.examAnswerRepository.findByIdQuestion(id);
        List<CorrectExamAnswerEntity> correctExamAnswersEntity = this.correctExamAnswerRepository.findByIdQuestion(id);

        examQuestionEntityOptional.get().setCourse(courseEntityOptional.get());
        examQuestionEntityOptional.get().setProfessor(professorEntityOptional.get());
        examQuestionEntityOptional.get().setQuestionText(examQuestionDto.getQuestionText());
        examQuestionEntityOptional.get().setId(id);

        //save the updated question
        examQuestionRepository.save(examQuestionEntityOptional.get());

        //delete the existing answers
        this.examAnswerRepository.deleteAll(examAnswersEntity);
        this.correctExamAnswerRepository.deleteAll(correctExamAnswersEntity);

        //create new answers
        for (ExamAnswerDto answer : examQuestionDto.getAnswersQuestions()){
            ExamAnswerEntity examAnswerEntity = ExamAnswerEntity.builder()
                    .answerText(answer.getAnswerText())
                    .question(examQuestionEntityOptional.get())
                    .build();

            this.examAnswerRepository.save(examAnswerEntity);

            if(answer.isCorrect()){
                CorrectExamAnswerEntity correctExamAnswerEntity = CorrectExamAnswerEntity.builder()
                        .answer(examAnswerEntity)
                        .question(examQuestionEntityOptional.get())
                        .build();

                this.correctExamAnswerRepository.save(correctExamAnswerEntity);
            }
        }
    }

    public void checkIfExistsById(Long id) throws EntityNotFoundException {
        if(!examQuestionRepository.existsById(id))
            throw new EntityNotFoundException("ExamQuestion", id.toString());
    }
}
