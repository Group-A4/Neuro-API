package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.mappers.AnswerExamMapper;
import com.example.Neurosurgical.App.mappers.CorrectAnswerExamMapper;
import com.example.Neurosurgical.App.mappers.QuestionExamMapper;
import com.example.Neurosurgical.App.models.dtos.*;
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
    final private ExamRepository examRepository;
    final private ProfessorRepository  professorRepository;

    @Autowired
    public QuestionExamServiceImpl(QuestionExamRepository questionExamRepository,
                                   AnswerExamRepository answerExamRepository,
                                   ExamRepository examRepository,
                                   ProfessorRepository professorRepository) {

        this.questionExamRepository = questionExamRepository;
        this.answerExamRepository = answerExamRepository;
        this.examRepository = examRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    public List<QuestionMultipleChoiceExamDto> findAllMultipleChoice() {
        List<QuestionMultipleChoiceExamDto> questionMultipleChoiceExamDtos;

        List<QuestionExamEntity> questionExamEntities = questionExamRepository.findMultipleChoiceQuestions();
        if (questionExamEntities.size() > 0){
            questionMultipleChoiceExamDtos = questionExamEntities.stream()
                    .map(QuestionExamMapper::toDto)
                    .collect(Collectors.toList());
        }
        else{
            throw new EntityNotFoundException("Exam Multiple Choice questions found");
        }

        return questionMultipleChoiceExamDtos;
    }

    @Override
    public List<QuestionLongResponseExamDto> findAllLongResponse() {
        List<QuestionLongResponseExamDto> questionMultipleChoiceExamDtos;

        List<QuestionExamEntity> questionExamEntities = questionExamRepository.findLongResponseQuestions();
        if (questionExamEntities.size() > 0){
            questionMultipleChoiceExamDtos = questionExamEntities.stream()
                    .map(QuestionExamMapper::toLongResponseDto)
                    .collect(Collectors.toList());
        }
        else{
            throw new EntityNotFoundException("Exam Long Response questions found");
        }

        return questionMultipleChoiceExamDtos;

    }

    @Override
    public void createMultipleChoiceQuestionExam(QuestionMultipleChoiceExamCreationDto questionExamDto, Long idExam) throws EntityNotFoundException {
        if(!this.examRepository.existsById(idExam)){
            throw new EntityNotFoundException("Exam with id " + idExam + " not found");
        }

        QuestionExamEntity questionExamEntity = QuestionExamMapper.fromCreationDto(questionExamDto);

        this.setExamAndProfessorForQuestion(questionExamDto,questionExamEntity,idExam);

        try{
            this.questionExamRepository.save(questionExamEntity);
        }
        catch (Exception e){
            throw new EntityAlreadyExistsException("Question already exists or invalid input");
        }

    }

    @Override
    public void createLongResponseQuestionExam(QuestionLongResponseExamCreationDto questionLongResponseDto, Long idExam) throws EntityNotFoundException {
        if(!this.examRepository.existsById(idExam)){
            throw new EntityNotFoundException("Exam with id " + idExam + " not found");
        }

        QuestionExamEntity questionExamEntity = QuestionExamMapper.fromLongResponseDto(questionLongResponseDto);

        this.setExamAndProfessorForQuestion(QuestionMultipleChoiceExamCreationDto.builder()
                        .idProfessor(questionLongResponseDto.getIdProfessor())
                        .build()
                ,questionExamEntity,idExam);

        //we also create the LongResponseQuestionEntity

        QuestionLongResponseExamEntity longResponseQuestionEntity =
                QuestionLongResponseExamEntity.builder()
                        .expectedResponse(questionLongResponseDto.getExpectedResponse())
                        .question(questionExamEntity)
                        .build();

        questionExamEntity.setQuestionLongResponseExam(longResponseQuestionEntity);

        try{
            this.questionExamRepository.save(questionExamEntity);
        }
        catch (Exception e){
            throw new EntityAlreadyExistsException("Question already exists or invalid input");
        }

    }

    @Override
    public void updateMultipleChoiceQuestionExam(QuestionMultipleChoiceExamDto questionMultipleChoiceExamDto, Long idQuestion) throws EntityNotFoundException {
        //idk why id doesn t work
        QuestionExamEntity questionExamEntity = questionExamRepository.findById(idQuestion)
                .orElseThrow(() -> new EntityNotFoundException("Question with ID " , idQuestion.toString()));

        this.answerExamRepository.deleteAll(this.answerExamRepository.findByIdQuestion(idQuestion));

        questionExamEntity.setQuestionText(questionMultipleChoiceExamDto.getQuestionText());

        questionExamEntity.setProfessor(this.professorRepository.findById(questionMultipleChoiceExamDto.getIdProfessor()).get());

        questionExamEntity.setPoints(questionMultipleChoiceExamDto.getPoints());

        List<AnswerExamEntity> updatedAnswers = new ArrayList<>();

        for (AnswerExamDto answerExamDto : questionMultipleChoiceExamDto.getAnswersQuestion()) {
            AnswerExamEntity answer = AnswerExamMapper.fromDto(answerExamDto, questionExamEntity);
            if(answerExamDto.isCorrect()){
                answer.setCorrectAnswerExam(CorrectAnswerExamMapper.fromAnswerExamEntity(answer));
            }
            updatedAnswers.add(answer);
        }

        questionExamEntity.setAnswersQuestion(updatedAnswers);

        try {
        this.questionExamRepository.save(questionExamEntity);
        }
        catch (Exception e){
            throw new EntityAlreadyExistsException("Cannot update question");
        }

    }

    @Override
    public void updateLongResponseQUestionExam(QuestionLongResponseExamCreationDto questionExamCreationDto, Long idQuestion) {

            QuestionExamEntity questionExamEntity = questionExamRepository.findById(idQuestion)
                    .orElseThrow(() -> new EntityNotFoundException("Question with ID " , idQuestion.toString()));

            questionExamEntity.setQuestionText(questionExamCreationDto.getQuestionText());

            questionExamEntity.setProfessor(this.professorRepository.findById(questionExamCreationDto.getIdProfessor()).get());
            //it should also be set the exam, and the course but the professor can't change a question from an exam/course to another

            questionExamEntity.setPoints(questionExamCreationDto.getPoints());

            questionExamEntity.getQuestionLongResponseExam().setExpectedResponse(questionExamCreationDto.getExpectedResponse());

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

    private void setExamAndProfessorForQuestion(QuestionMultipleChoiceExamCreationDto questionExamDto, QuestionExamEntity questionExamEntity, Long idExam) throws EntityNotFoundException {
        ExamEntity examForQuestion = this.examRepository.findById(idExam).get();

        questionExamEntity.setExam(examForQuestion);

        Optional<ProfessorEntity> professor = professorRepository.findById(questionExamDto.getIdProfessor());

        if( professor.isEmpty() ){
            throw new EntityNotFoundException("Professor with id " + questionExamDto.getIdProfessor() + " not found");
        }
        questionExamEntity.setProfessor(professor.get());
    }
}
