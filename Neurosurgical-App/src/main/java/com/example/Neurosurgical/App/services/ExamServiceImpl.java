package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.mappers.ExamMapper;
import com.example.Neurosurgical.App.mappers.QuestionExamMapper;
import com.example.Neurosurgical.App.models.dtos.ExamDto;
import com.example.Neurosurgical.App.models.dtos.QuestionExamDto;
import com.example.Neurosurgical.App.models.entities.CourseEntity;
import com.example.Neurosurgical.App.models.entities.ExamEntity;
import com.example.Neurosurgical.App.models.entities.QuestionExamEntity;
import com.example.Neurosurgical.App.models.entities.ProfessorEntity;
import com.example.Neurosurgical.App.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ExamServiceImpl implements ExamService {
    final private ExamRepository examRepository;
    final private CourseRepository courseRepository;
    final private ProfessorRepository professorRepository;
    final private QuestionExamRepository questionExamRepository;
    final private AnswerExamRepository answerExamRepository;
    final private CorrectAnswerExamRepository correctAnswerExamRepository;

    @Autowired
    public ExamServiceImpl(ExamRepository examRepository,
                           CourseRepository courseRepository,
                           ProfessorRepository professorRepository,
                           QuestionExamRepository questionExamRepository,
                           AnswerExamRepository answerExamRepository,
                           CorrectAnswerExamRepository correctAnswerExamRepository) {
        this.examRepository = examRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
        this.questionExamRepository = questionExamRepository;
        this.answerExamRepository = answerExamRepository;
        this.correctAnswerExamRepository = correctAnswerExamRepository;
    }

    @Override
    public Optional<List<QuestionExamDto>> findById(Long id) throws EntityNotFoundException {
        //return the exam questions with the specified id
        List<QuestionExamDto> exam = new ArrayList<>();

        Optional<ArrayList<Long>> questionsIds = this.questionExamRepository.findByExamId(id);
        if(questionsIds.isEmpty()){
            throw new EntityNotFoundException("Question", id);
        }

        int size = questionsIds.orElse(new ArrayList<>()).size();
        for(int i=0; i<size; i++){
            Optional<QuestionExamEntity> opt = this.questionExamRepository.findByQuestionId(questionsIds.get().get(i));

            if(opt.isEmpty()){
                throw new EntityNotFoundException("Question", id);
            }

            exam.add(QuestionExamMapper.toDto(
                    opt.get(),
                    this.answerExamRepository.findByIdQuestion(opt.get().getId()),
                    this.correctAnswerExamRepository.findByIdQuestion(opt.get().getId())
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

    @Override
    public Optional<List<ExamDto>> findByProfessorId(Long id) throws EntityNotFoundException{
        List<ExamDto> exams = new ArrayList<>();

        Optional<ArrayList<ExamEntity>> examsEntity = this.examRepository.findByIdProfessor(id);
        if (examsEntity.isEmpty())
            throw new EntityNotFoundException("Exam", id);

        int size = examsEntity.orElse(new ArrayList<>()).size();
        for(int i=0; i<size; i++){
            Optional<ExamEntity> opt = this.examRepository.findById(examsEntity.get().get(i).getId());

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
    public Optional<ExamDto> findByCode(String code) throws EntityNotFoundException{


        Optional<ExamEntity> examEntity = this.examRepository.findByCode(code);
        if(examEntity.isEmpty()){
            throw new EntityNotFoundException("Exam", code);
        }

        ExamDto exam = ExamMapper.toDto(examEntity.get());
        return Optional.of(exam);
    }

    public void createExam(ExamDto examDto) throws EntityNotFoundException {
        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(examDto.getIdCourse());
        Optional<ProfessorEntity> professorEntityOptional = professorRepository.findById(examDto.getIdProfessor());

        if(courseEntityOptional.isEmpty())
            throw new EntityNotFoundException("Course",examDto.getIdCourse());
        if(professorEntityOptional.isEmpty())
            throw new EntityNotFoundException("Professor", examDto.getIdProfessor());

        //construct the code
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        StringBuilder alphabet = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        do {
            for(int i=0; i<6; i++){
                int index = random.nextInt(alphabet.length());
                code.append(alphabet.charAt(index));
            }
        } while (examRepository.findByCode(code.toString()).isPresent());

        //get the questions
        List<QuestionExamEntity> questionExamEntityList = new ArrayList<>();
        for(QuestionExamDto questionDto : examDto.getQuestions()){
            questionExamEntityList.add(QuestionExamMapper.fromDto(questionDto));
        }

        //save the exam without the question list so that
        ExamEntity examEntity = ExamEntity.builder()
                .course(courseEntityOptional.get())
                .professor(professorEntityOptional.get())
                .title(examDto.getTitle())
                .time(examDto.getTime())
                .code(code.toString())
                .examQuestions(questionExamEntityList)
                .build();

        examRepository.save(examEntity);
    }
}
