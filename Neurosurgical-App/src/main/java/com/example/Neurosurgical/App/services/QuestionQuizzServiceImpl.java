package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.mappers.AnswerQuizzMapper;
import com.example.Neurosurgical.App.mappers.QuestionQuizzMapper;
import com.example.Neurosurgical.App.models.dtos.AnswerQuizzDto;
import com.example.Neurosurgical.App.models.dtos.QuestionQuizzCreationDto;
import com.example.Neurosurgical.App.models.dtos.QuestionQuizzDto;
import com.example.Neurosurgical.App.models.entities.*;
import com.example.Neurosurgical.App.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionQuizzServiceImpl implements QuestionQuizzService {
    final private QuestionQuizzRepository questionQuizzRepository;
    final private AnswerQuizzRepository answerQuizzRepository;
    final private CourseRepository courseRepository;
    final private ProfessorRepository professorRepository;
    final private LectureRepository lectureRepository;

    @Autowired
    public QuestionQuizzServiceImpl(QuestionQuizzRepository questionQuizzRepository,
                                    CourseRepository courseRepository,
                                    ProfessorRepository professorRepository,
                                    AnswerQuizzRepository answerQuizzRepository,
                                    LectureRepository lectureRepository){

        this.questionQuizzRepository = questionQuizzRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
        this.answerQuizzRepository = answerQuizzRepository;
        this.lectureRepository = lectureRepository;

    }

    @Override
    public Optional<QuestionQuizzDto> findById(Long id) throws EntityNotFoundException {
        Optional<QuestionQuizzEntity> questionQuizz = questionQuizzRepository.findById(id);

        if(questionQuizz.isEmpty()){
            throw new EntityNotFoundException("QuestionQuizz with id=", id.toString());
        }

        return Optional.of(QuestionQuizzMapper.toDto(questionQuizz.get()));

    }

    @Override
    public List<QuestionQuizzDto> findAll() {
        List<QuestionQuizzDto> questionQuizzDtos ;

        List<QuestionQuizzEntity> questionQuizzEntities = questionQuizzRepository.findAll();
        if (questionQuizzEntities.size() > 0){
            questionQuizzDtos = questionQuizzEntities.stream()
                    .map(QuestionQuizzMapper::toDto)
                    .collect(Collectors.toList());
        }
        else{
            throw new EntityNotFoundException("No Quizz questions found");
        }

        return questionQuizzDtos;

    }

    @Override
    public Optional<List<QuestionQuizzDto>> findByIdProfessorAndIdCourse(Long idProfessor, Long idCourse) throws EntityNotFoundException {
        Optional<List<QuestionQuizzEntity>> list
                = questionQuizzRepository.findByIdProfessorAndIdCourse(idProfessor, idCourse);

        if(list.get().isEmpty()){
            throw new EntityNotFoundException(" questions idProf="+ idProfessor + " and courseId=", idCourse.toString());
        }

        return Optional.of(
                list.get()
                        .stream()
                        .map(QuestionQuizzMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<List<QuestionQuizzDto>> findByIdProfessor(Long id) throws EntityNotFoundException {
        Optional<List<QuestionQuizzEntity>> list
                = questionQuizzRepository.findByIdProfessor(id);

        if(list.get().isEmpty()){
            throw new EntityNotFoundException("question whith idProf = ", id.toString());
        }
        return Optional.of(
                list.get()
                        .stream()
                        .map(QuestionQuizzMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<List<QuestionQuizzDto>> findByIdCourse(Long id) throws EntityNotFoundException {
        Optional<List<QuestionQuizzEntity>> list
                = questionQuizzRepository.findByIdCourse(id);

        if(list.get().isEmpty()){
            throw new EntityNotFoundException("questions with idCourse=", id.toString());
        }

        return Optional.of(
                list.get()
                        .stream()
                        .map(QuestionQuizzMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<List<QuestionQuizzDto>> findByIdCourseAndLectureNumber(Long idCourse, Long idLecture) throws EntityNotFoundException {
        Optional<List<QuestionQuizzEntity>> list
                = questionQuizzRepository.findByIdCourseAndLectureNumber(idCourse, idLecture);
        if(list.isEmpty())
            throw new EntityNotFoundException("questions with idCourse=", idCourse + " and lectureNumber=" + idLecture);

        return Optional.of(
                list.get()
                        .stream()
                        .map(QuestionQuizzMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void createQuestionQuizz(QuestionQuizzCreationDto questionQuizzDto) throws EntityNotFoundException {

        long lectureId = questionQuizzDto.getIdLecture();
        long professorId = questionQuizzDto.getIdProfessor();

        Optional<LectureEntity> lectureEntityOptional = lectureRepository.findById(lectureId);
        Optional<ProfessorEntity> professorEntityOptional = professorRepository.findById(professorId);

        if(lectureEntityOptional.isEmpty()) throw new EntityNotFoundException("lecture", lectureId);
        if(professorEntityOptional.isEmpty()) throw new EntityNotFoundException("professor", professorId);

        QuestionQuizzEntity questionQuizzEntity = QuestionQuizzMapper.fromCreationDto(questionQuizzDto);

        questionQuizzEntity.setLecture(lectureEntityOptional.get());
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

        QuestionQuizzEntity questionQuizzEntity = questionQuizzRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question with ID " , id.toString()));


        this.answerQuizzRepository.deleteAll(this.answerQuizzRepository.findByIdQuestion(id));

        questionQuizzEntity.setDifficulty(questionQuizzDto.getDifficulty());
        questionQuizzEntity.setQuestionText(questionQuizzDto.getQuestionText());
        questionQuizzEntity.setTimeMinutes(questionQuizzDto.getTimeMinutes());

        questionQuizzEntity.setLecture(this.lectureRepository.findById(questionQuizzDto.getIdLecture()).get());
        questionQuizzEntity.setProfessor(this.professorRepository.findById(questionQuizzDto.getIdProfessor()).get());

        List<AnswerQuizzEntity> updatedAnswers = new ArrayList<>();
        for(AnswerQuizzDto answerQuizzDto : questionQuizzDto.getAnswersQuestion()){
            AnswerQuizzEntity answerQuizzEntity = AnswerQuizzMapper.fromDto(answerQuizzDto,questionQuizzEntity);
            updatedAnswers.add(answerQuizzEntity);
        }

        questionQuizzEntity.setAnswersQuestion(updatedAnswers);

        questionQuizzRepository.save(questionQuizzEntity);
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
            throw new EntityNotFoundException("QuestionQuizz with id=", id.toString());
    }
}
