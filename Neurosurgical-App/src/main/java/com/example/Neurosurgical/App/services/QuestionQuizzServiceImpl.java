package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.mappers.QuestionQuizzMapper;
import com.example.Neurosurgical.App.models.dtos.AnswerQuizzDto;
import com.example.Neurosurgical.App.models.dtos.QuestionQuizzDto;
import com.example.Neurosurgical.App.models.entities.*;
import com.example.Neurosurgical.App.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionQuizzServiceImpl implements QuestionQuizzService {
    final private QuestionQuizzRepository questionQuizzRepository;
    final private AnswerQuizzRepository answerQuizzRepository;
    final private CorrectAnswerQuizzRepository correctAnswerQuizzRepository;
    final private CourseRepository courseRepository;
    final private ProfessorRepository professorRepository;

    @Autowired
    public QuestionQuizzServiceImpl(QuestionQuizzRepository questionQuizzRepository,
                                    CourseRepository courseRepository,
                                    ProfessorRepository professorRepository,
                                    AnswerQuizzRepository answerQuizzRepository,
                                    CorrectAnswerQuizzRepository correctAnswerQuizzRepository) {

        this.questionQuizzRepository = questionQuizzRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
        this.answerQuizzRepository = answerQuizzRepository;
        this.correctAnswerQuizzRepository = correctAnswerQuizzRepository;

    }

    @Override
    public Optional<QuestionQuizzDto> findById(Long id) throws EntityNotFoundException {
        Optional<QuestionQuizzEntity> list = questionQuizzRepository.findById(id);

        if(list.isEmpty()){
            throw new EntityNotFoundException("QuestionQuizz", id.toString());
        }

        return Optional.of(QuestionQuizzMapper.toDto(list.get(),
                this.answerQuizzRepository.findByIdQuestion(id),
                this.correctAnswerQuizzRepository.findByIdQuestion(id))
        );

    }

    @Override
    public List<QuestionQuizzDto> findAll() {

        return questionQuizzRepository.findAll().stream()
                        .map( questionEntity -> QuestionQuizzMapper.toDto(questionEntity,
                                this.answerQuizzRepository.findByIdQuestion(questionEntity.getId()),
                                this.correctAnswerQuizzRepository.findByIdQuestion(questionEntity.getId())) )
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
                        .map( questionEntity -> QuestionQuizzMapper.toDto(questionEntity,
                                this.answerQuizzRepository.findByIdQuestion(questionEntity.getId()),
                                this.correctAnswerQuizzRepository.findByIdQuestion(questionEntity.getId())) )
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<List<QuestionQuizzDto>> findByIdProfessor(Long id) throws EntityNotFoundException {
        Optional<List<QuestionQuizzEntity>> list
                = questionQuizzRepository.findByIdProfessor(id);
        if(list.isEmpty())
            throw new EntityNotFoundException("QuestionQuizz", id.toString());

        return Optional.of(
                list.get()
                        .stream()
                        .map( questionEntity -> QuestionQuizzMapper.toDto(questionEntity,
                                this.answerQuizzRepository.findByIdQuestion(questionEntity.getId()),
                                this.correctAnswerQuizzRepository.findByIdQuestion(questionEntity.getId())) )
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<List<QuestionQuizzDto>> findByIdCourse(Long id) throws EntityNotFoundException {
        Optional<List<QuestionQuizzEntity>> list
                = questionQuizzRepository.findByIdCourse(id);
        if(list.isEmpty())
            throw new EntityNotFoundException("QuestionQuizz", id.toString());

        return Optional.of(
                list.get()
                        .stream()
                        .map( questionEntity -> QuestionQuizzMapper.toDto(questionEntity,
                                this.answerQuizzRepository.findByIdQuestion(questionEntity.getId()),
                                this.correctAnswerQuizzRepository.findByIdQuestion(questionEntity.getId())) )
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<List<QuestionQuizzDto>> findByIdCourseAndLectureNumber(Long idCourse, Integer lectureNumber) throws EntityNotFoundException {
        Optional<List<QuestionQuizzEntity>> list
                = questionQuizzRepository.findByIdCourseAndLectureNumber(idCourse, lectureNumber);
        if(list.isEmpty())
            throw new EntityNotFoundException("QuestionQuizz", idCourse.toString());

        return Optional.of(
                list.get()
                        .stream()
                        .map( questionEntity -> QuestionQuizzMapper.toDto(questionEntity,
                                this.answerQuizzRepository.findByIdQuestion(questionEntity.getId()),
                                this.correctAnswerQuizzRepository.findByIdQuestion(questionEntity.getId())) )
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<List<Integer>> getLecturesByIdCourse(Long idCourse) throws EntityNotFoundException{
        Optional<List<Integer>> list = questionQuizzRepository.getLecturesByIdCourse(idCourse);
        if(list.isEmpty())
            throw new EntityNotFoundException("QuestionQuizz", idCourse.toString());

        return list;
    }


    @Override
    public void createQuestionQuizz(QuestionQuizzDto questionQuizzDto) throws EntityNotFoundException {

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
    public void deleteQuestionQuizzById(Long id) throws EntityNotFoundException {
        checkIfExistsById(id);
        questionQuizzRepository.deleteById(id);
    }

    @Override
    public void updateQuestionQuizz(Long id, QuestionQuizzDto questionQuizzDto) throws EntityNotFoundException {

        checkIfExistsById(id);

        long courseId = questionQuizzDto.getIdCourse();
        long professorId = questionQuizzDto.getIdProfessor();

        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(courseId);
        Optional<ProfessorEntity> professorEntityOptional = professorRepository.findById(professorId);

        if (courseEntityOptional.isEmpty()) throw new EntityNotFoundException("course", courseId);
        if (professorEntityOptional.isEmpty()) throw new EntityNotFoundException("professor", professorId);

        Optional<QuestionQuizzEntity> questionQuizzEntityOptional = this.questionQuizzRepository.findById(id);

        if (questionQuizzEntityOptional.isEmpty())
            throw new EntityNotFoundException("question", id);

        //find answers
        List<AnswerQuizzEntity> answerQuizzEntity = this.answerQuizzRepository.findByIdQuestion(id);
        List<CorrectAnswerQuizzEntity> correctAnswerQuizzEntity = this.correctAnswerQuizzRepository.findByIdQuestion(id);

        questionQuizzEntityOptional.get().setCourse(courseEntityOptional.get());
        questionQuizzEntityOptional.get().setProfessor(professorEntityOptional.get());
        questionQuizzEntityOptional.get().setQuestionText(questionQuizzDto.getQuestionText());
        if(questionQuizzDto.getTimeMinutes() != null) {
            questionQuizzEntityOptional.get().setTimeMinutes(questionQuizzDto.getTimeMinutes());
        }
        if(questionQuizzDto.getDifficulty() != null){
            questionQuizzEntityOptional.get().setDifficulty(questionQuizzDto.getDifficulty());
        }

        questionQuizzEntityOptional.get().setLectureNumber(questionQuizzDto.getLectureNumber());
        questionQuizzEntityOptional.get().setId(id);

        //save updated question
        questionQuizzRepository.save(questionQuizzEntityOptional.get());

        //delete all existing answers
        this.answerQuizzRepository.deleteAll(answerQuizzEntity);
        this.correctAnswerQuizzRepository.deleteAll(correctAnswerQuizzEntity);

        //create new answers based on the dto
        for (AnswerQuizzDto answer : questionQuizzDto.getAnswersQuestion()) {
            AnswerQuizzEntity answerQuizzEntity1 = AnswerQuizzEntity.builder()
                    .answerText(answer.getAnswerText())
                    .question(questionQuizzEntityOptional.get())
                    .build();

            this.answerQuizzRepository.save(answerQuizzEntity1);

            if (answer.isCorrect()) {
                CorrectAnswerQuizzEntity correctAnswerQuizzEntity1 = CorrectAnswerQuizzEntity.builder()
                        .answer(answerQuizzEntity1)
                        .question(questionQuizzEntityOptional.get())
                        .build();

                this.correctAnswerQuizzRepository.save(correctAnswerQuizzEntity1);
            }
        }

    }

    @Override
    public void updateQuestionQuizzText(Long id, String questionText) throws EntityNotFoundException {

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
