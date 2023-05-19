package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.advice.exceptions.InvalidDateException;
import com.example.Neurosurgical.App.mappers.ExamMapper;
import com.example.Neurosurgical.App.mappers.QuestionExamMapper;
import com.example.Neurosurgical.App.models.dtos.*;
import com.example.Neurosurgical.App.models.entities.*;
import com.example.Neurosurgical.App.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ExamServiceImpl implements ExamService {

    final private ExamRepository examRepository;

    final private QuestionExamRepository questionExamRepository;
    final private CourseRepository courseRepository;

    final private ProfessorRepository professorRepository;

    final private StudentRepository studentRepository;
    final private StudentTookExamsRepository studentTookExamsRepository;

    final private StudentQuestionPointsRepository studentQuestionPointsRepository;

    final private StudentMultipleChoiceResponsesRepository studentMultipleChoiceResponsesRepository;

    final private StudentLongResponsesRepository studentLongResponsesRepository;

    final private CorrectAnswerExamRepository correctAnswerExamRepository;
    final private ActiveExamRepository activeExamRepository;


    @Autowired
    public ExamServiceImpl(ExamRepository examRepository,
                           QuestionExamRepository questionExamRepository,
                           CourseRepository courseRepository,
                           ProfessorRepository professorRepository,
                           StudentRepository studentRepository,
                           StudentTookExamsRepository studentTookExamsRepository,
                           StudentQuestionPointsRepository studentQuestionPointsRepository,
                           StudentMultipleChoiceResponsesRepository studentMultipleChoiceResponsesRepository,
                           StudentLongResponsesRepository studentLongResponsesRepository,
                           CorrectAnswerExamRepository correctAnswerExamRepository,
                           ActiveExamRepository activeExamRepository) {

        this.examRepository = examRepository;
        this.questionExamRepository = questionExamRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
        this.studentTookExamsRepository = studentTookExamsRepository;
        this.studentQuestionPointsRepository = studentQuestionPointsRepository;
        this.studentMultipleChoiceResponsesRepository = studentMultipleChoiceResponsesRepository;
        this.studentLongResponsesRepository = studentLongResponsesRepository;
        this.correctAnswerExamRepository = correctAnswerExamRepository;
        this.activeExamRepository = activeExamRepository;
    }


    @Override
    public void createExam(ExamCreationDto examCreationDto) throws InvalidDateException {
        CourseEntity courseEntity = this.courseRepository.findById(examCreationDto.getIdCourse())
                .orElseThrow(() -> new EntityNotFoundException("Course", examCreationDto.getIdCourse()));

        ProfessorEntity professorEntity = this.professorRepository.findById(examCreationDto.getIdProfessor())
                .orElseThrow(() -> new EntityNotFoundException("Professor", examCreationDto.getIdProfessor()));

        LocalDateTime todayDate = LocalDate.now().atStartOfDay();
        LocalDateTime examDate = examCreationDto.getDate().toLocalDateTime();

        if(examDate.isBefore(todayDate)){
            throw new InvalidDateException("Exam date(" + examDate + ") is before today");
        }


        ExamEntity examEntity = ExamMapper.fromCreationDto(examCreationDto);

        examEntity.setCourse(courseEntity);
        examEntity.setProfessor(professorEntity);
        examEntity.setCode(this.generateCode());


        List<QuestionExamEntity> questionsExam = new ArrayList<>();
        for(QuestionMultipleChoiceExamCreationDto questionMultipleChoiceExamCreationDto : examCreationDto.getQuestionsMultipleChoice()){
            QuestionExamEntity questionExamEntity = QuestionExamMapper.fromCreationDto(questionMultipleChoiceExamCreationDto);
            questionExamEntity.setProfessor(professorEntity);
            questionExamEntity.setExam(examEntity);
            questionsExam.add(questionExamEntity);
        }

        for(QuestionLongResponseExamCreationDto questionLongResponseExamCreationDto : examCreationDto.getQuestionsLongResponse()){
            QuestionExamEntity questionExamEntity = QuestionExamMapper.fromLongResponseDto(questionLongResponseExamCreationDto);
            questionExamEntity.setProfessor(professorEntity);
            questionExamEntity.setExam(examEntity);
            QuestionLongResponseExamEntity questionLongResponseExamEntity =
                    QuestionLongResponseExamEntity.builder()
                                    .expectedResponse(questionLongResponseExamCreationDto.getExpectedResponse())
                                    .question(questionExamEntity)
                                    .build();
            questionExamEntity.setQuestionLongResponseExam(questionLongResponseExamEntity);
            questionsExam.add(questionExamEntity);
        }

        examEntity.setQuestionsExam(questionsExam);

        try {
            this.examRepository.save(examEntity);
        } catch (Exception e){
            throw new EntityAlreadyExistsException("Exam already exists or invalid input");
        }

    }

    @Override
    public ExamDto findByCode(String code) {

        ExamEntity examEntity = this.examRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Exam", code));

        return ExamMapper.toDto(examEntity, true);

    }

    @Override
    public ExamDto findByCodeForStudent(String code, Long idStudent) {

        this.studentRepository.findById(idStudent)
                .orElseThrow(() -> new EntityNotFoundException("Student",idStudent));

        ExamEntity examEntity = this.examRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Exam", code));

        if ( this.activeExamRepository.findById(examEntity.getId()).isEmpty()){
            throw new EntityNotFoundException("Active exam ", code);
        }

        if(this.studentTookExamsRepository.findByIdStudentAndIdExam(idStudent, examEntity.getId()).isPresent()){
            throw new EntityAlreadyExistsException("Student already took exam");
        }

        return ExamMapper.toDto(examEntity,true);
    }

    @Override
    public List<ExamSummariseDto> findByIdStudent(Long idStudent) {
        List<ExamEntity> examEntities = this.examRepository.findByIdStudent(idStudent);

        if (examEntities.isEmpty()){
            throw new EntityNotFoundException("Exams for student ", idStudent);
        }

        return examEntities.stream().map(ExamMapper::toSummariseDto).toList();

    }

    @Override
    public List<ExamSummariseDto> findByIdCourse(Long idCourse) {
        List<ExamEntity> examEntities = this.examRepository.findByIdCourse(idCourse);

        if (examEntities.isEmpty()){
            throw new EntityNotFoundException("Exams for course ", idCourse);
        }

        return examEntities.stream().map(ExamMapper::toSummariseDto).toList();
    }

    @Override
    public ExamResultDto viewExamResult(Long idExam, Long idStudent) {
        ExamEntity examEntity = this.examRepository.findById(idExam)
                .orElseThrow(() -> new EntityNotFoundException("Exam", idExam));
        if(this.studentRepository.findById(idStudent).isEmpty())
            throw new EntityNotFoundException("Student", idStudent);

        StudentTookExamsEntity studentTookExamsEntity = this.studentTookExamsRepository.findByIdStudentAndIdExam(idStudent, idExam)
                .orElseThrow(() -> new EntityNotFoundException("Exam result for student ", idStudent));

        ExamResultDto examResultDto = ExamResultDto.builder()
                .id(idExam)
                .idCourse(examEntity.getCourse().getId())
                .idProfessor(examEntity.getProfessor().getIdUser())
                .title(examEntity.getTitle())
                .date(examEntity.getDate())
                .timeExam(examEntity.getTimeExam())
                .evaluationType(examEntity.getEvaluationType())
                .build();
        // for every question in examEntity, add to examResulDto(then add points, student's answer, correct answer)
        ExamDto examDto = ExamMapper.toDto(examEntity, false);

        List<QuestionMultipleChoiceExamDto> questionsMultipleChoice = examDto.getQuestionsMultipleChoice();
        List<QuestionLongResponseExamDto> questionsLongResponse = examDto.getQuestionsLongResponse();

        // new lists
        List<QuestionMultipleChoiceExamResultDto> questionsMultipleChoiceResult = new ArrayList<>();

        double totalPoints = 0.0;
        for(QuestionMultipleChoiceExamDto questionDto : questionsMultipleChoice){
            double studentPoints = this.studentQuestionPointsRepository.findByIdStudentAndIdQuestion(idStudent, questionDto.getId()).get().getPointsGiven();
            totalPoints += studentPoints;

            long idStudentQuestionPoints = this.studentQuestionPointsRepository.findByIdStudentAndIdQuestion(idStudent, questionDto.getId()).get().getId();

            List<AnswerExamResultDto> answers = new ArrayList<>();
            for(AnswerExamDto answerDto : questionDto.getAnswers()){
                boolean isChosenByStudent = this.studentMultipleChoiceResponsesRepository.findByIdStudentQuestionPointsAndIdAnswer(idStudentQuestionPoints, answerDto.getId()).isPresent();

                AnswerExamResultDto answerExamResultDto = AnswerExamResultDto.builder()
                        .id(answerDto.getId())
                        .idQuestion(questionDto.getId())
                        .answerText(answerDto.getAnswerText())
                        .isCorrect(answerDto.isCorrect())
                        .isChosenByStudent(isChosenByStudent)
                        .build();
                answers.add(answerExamResultDto);
            }

            QuestionMultipleChoiceExamResultDto resultDto = QuestionMultipleChoiceExamResultDto.builder()
                    .id(questionDto.getId())
                    .idExam(idExam)
                    .idProfessor(examEntity.getProfessor().getIdUser())
                    .questionText(questionDto.getQuestionText())
                    .points(questionDto.getPoints())
                    .studentPoints(studentPoints)
                    .answersQuestionResult(answers)
                    .build();
            questionsMultipleChoiceResult.add(resultDto);
        }
        examResultDto.setQuestionsMultipleChoiceResult(questionsMultipleChoiceResult);


        // time for long response questions
        List<QuestionLongResponseExamResultDto> questionsLongResponseResult = new ArrayList<>();

        for(QuestionLongResponseExamDto longQuestionDto : questionsLongResponse) {
            double studentPoints = this.studentQuestionPointsRepository.findByIdStudentAndIdQuestion(idStudent, longQuestionDto.getId()).get().getPointsGiven();
            totalPoints += studentPoints;

            long idStudentQuestionPoints = this.studentQuestionPointsRepository.findByIdStudentAndIdQuestion(idStudent, longQuestionDto.getId()).get().getId();

            String studentResponse = this.studentLongResponsesRepository.findByIdStudentQuestionPoints(idStudentQuestionPoints).get(0).getStudentResponse();

            QuestionLongResponseExamResultDto resultDto = QuestionLongResponseExamResultDto.builder()
                    .id(longQuestionDto.getId())
                    .idExam(idExam)
                    .idProfessor(examEntity.getProfessor().getIdUser())
                    .questionText(longQuestionDto.getQuestionText())
                    .points(longQuestionDto.getPoints())
                    .studentPoints(studentPoints)
                    .expectedResponse(longQuestionDto.getExpectedResponse())
                    .studentResponse(studentResponse)
                    .build();
            questionsLongResponseResult.add(resultDto);
        }
        examResultDto.setQuestionsLongResponseResult(questionsLongResponseResult);

        examResultDto.setTotalPoints(totalPoints);

        return examResultDto;
    }

    @Override
    public List<ExamPointsDto> getPoints(Long idStudent) {

        if ( this.studentRepository.findById(idStudent).isEmpty() ){
            throw new EntityNotFoundException("Student", idStudent);
        }

        List<StudentTookExamsEntity> studentTookExamsEntities = this.studentTookExamsRepository.findByIdStudent(idStudent);

        if (studentTookExamsEntities.size() == 0 ){
            throw new EntityNotFoundException("Exams for student ", idStudent);
        }

        List<ExamPointsDto> examPointsDtos = new ArrayList<>();
        for( StudentTookExamsEntity studentTookExams : studentTookExamsEntities){
            examPointsDtos.add(ExamPointsDto.builder()
                    .idExam(studentTookExams.getIdExam())
                    .pointsExam(studentTookExams.getPointsPerExam())
                    .build());
        }

        return examPointsDtos;

    }

    @Override
    public void activateExam(Long idExam) {

        this.examRepository.findById(idExam)
                .orElseThrow(() -> new EntityNotFoundException("Exam", idExam));

        this.activeExamRepository.save(ActiveExamEntity.builder()
                        .idExam(idExam)
                        .build());
    }

    @Override
    public void deactivateExam(Long idExam) {

        ActiveExamEntity activeExamEntity = this.activeExamRepository.findById(idExam)
                .orElseThrow(() -> new EntityNotFoundException("Active exam", idExam));

        this.activeExamRepository.delete(activeExamEntity);
    }

    @Override
    public void evaluateExam(ExamDto examDto, Long idStudent) {
        Long idExam = examDto.getId();
        ExamEntity examEntity = this.examRepository.findById(idExam)
                .orElseThrow(() -> new EntityNotFoundException("Exam", idExam));
        if(this.studentRepository.findById(idStudent).isEmpty())
            throw new EntityNotFoundException("Student", idStudent);

        Integer evaluationType = examEntity.getEvaluationType();

        if (evaluationType == TypeEvaluation.PERFECT_MATCH.ordinal()){
            perfectMatchEvaluation(examDto, idStudent);
        } else if (evaluationType == TypeEvaluation._1_WRONG_ANSWER__1_CORRECT_ANSWER.ordinal()){
            partialMatchEvaluation(examDto, idStudent, 1);
        } else if (evaluationType == TypeEvaluation._1_WRONG_ANSWER__2_CORRECT_ANSWER.ordinal()){
            partialMatchEvaluation(examDto, idStudent, 2);
        } else {
            throw new EntityNotFoundException("Evaluation type", Long.valueOf(evaluationType));
        }
    }

    @Override
    public void evaluateLongResponseQuestion(Long idStudent, Long idQuestion, Double points) {
        QuestionExamEntity questionExamEntity = this.questionExamRepository.findById(idQuestion)
                .orElseThrow(() -> new EntityNotFoundException("QuestionExam", idQuestion));

        if(this.studentRepository.findById(idStudent).isEmpty())
            throw new EntityNotFoundException("Student", idStudent);

        StudentQuestionPointsEntity entity = this.studentQuestionPointsRepository.findByIdStudentAndIdQuestion(idStudent, idQuestion)
                .orElseThrow(() -> new EntityNotFoundException("StudentQuestionPoints", idStudent + " | " + idQuestion));



        double previousPoints = entity.getPointsGiven();

        entity.setPointsGiven(points);
        this.studentQuestionPointsRepository.save(entity);

        // find the exam id that question belongs to
        Long idExam = questionExamEntity.getExam().getId();

        StudentTookExamsEntity studentTookExamsEntity = this.studentTookExamsRepository.findByIdStudentAndIdExam(idStudent, idExam)
                .orElseThrow(() -> new EntityNotFoundException("StudentTookExams", idStudent + " | " + idExam));

        double totalPoints = studentTookExamsEntity.getPointsPerExam();
        totalPoints = totalPoints - previousPoints + points;

        studentTookExamsEntity.setPointsPerExam(totalPoints);
        this.studentTookExamsRepository.save(studentTookExamsEntity);
    }

    // an incorrect answer cancels x correct answers
    private void partialMatchEvaluation(ExamDto examDto, Long idStudent, int decrement) {
        StudentTookExamsEntity studentTookExamsEntity = new StudentTookExamsEntity();
        studentTookExamsEntity.setIdStudent(idStudent);
        studentTookExamsEntity.setIdExam(examDto.getId());

        double totalPoints = 0.0;

        // evaluate only multiple choice questions
        List<QuestionMultipleChoiceExamDto> questionsMultipleChoice = examDto.getQuestionsMultipleChoice();
        for (QuestionMultipleChoiceExamDto question : questionsMultipleChoice) {
            List<AnswerExamDto> answers = question.getAnswers();
            int countStudentCorrectAnswers = 0;
            int countCorrectAnswers = 0;

            StudentQuestionPointsEntity studentQuestionPointsEntity = new StudentQuestionPointsEntity();
            studentQuestionPointsEntity.setIdStudent(idStudent);
            studentQuestionPointsEntity.setIdQuestion(question.getId());

            for(AnswerExamDto answer : answers){
                if(answer.isCorrect()){
                    // check if in correctAnswers there is an answer with the same id
                    if(this.correctAnswerExamRepository.findByIdAnswer(answer.getId()).isPresent()){
                        countStudentCorrectAnswers += 1;
                        countCorrectAnswers += 1;
                    } else {
                        countStudentCorrectAnswers -= decrement;
                    }

                    // save the answer of the student
                    StudentMultipleChoiceResponsesEntity studentMultipleChoiceResponsesEntity = new StudentMultipleChoiceResponsesEntity();
                    studentMultipleChoiceResponsesEntity.setStudentQuestionPoints(studentQuestionPointsEntity);
                    studentMultipleChoiceResponsesEntity.setIdAnswer(answer.getId());

                    // add the answer to the list of answers of the student
                    studentQuestionPointsEntity.getStudentMultipleChoiceResponsesList()
                            .add(studentMultipleChoiceResponsesEntity);
                } else {
                    // if the student did not select a correct answer, decrease the count
                    if(this.correctAnswerExamRepository.findByIdAnswer(answer.getId()).isPresent()){
                        countStudentCorrectAnswers -= decrement;
                        countCorrectAnswers += 1;
                    }
                }
            }

            double pointsPerCorrectAnswer = question.getPoints() / countCorrectAnswers;
            double points = 0.0;
            if(countStudentCorrectAnswers > 0)
                points = countStudentCorrectAnswers * pointsPerCorrectAnswer;

            // save the points of the student
            studentQuestionPointsEntity.setPointsGiven(points);
            this.studentQuestionPointsRepository.save(studentQuestionPointsEntity);

            totalPoints += points;
        }

        saveStudentLongResponses(examDto, idStudent);

        // save the total points of the student
        studentTookExamsEntity.setPointsPerExam(totalPoints);
        this.studentTookExamsRepository.save(studentTookExamsEntity);
    }

    private void perfectMatchEvaluation(ExamDto examDto, Long idStudent) {
        StudentTookExamsEntity studentTookExamsEntity = new StudentTookExamsEntity();
        studentTookExamsEntity.setIdStudent(idStudent);
        studentTookExamsEntity.setIdExam(examDto.getId());

        double totalPoints = 0.0;

        // evaluate only multiple choice questions
        List<QuestionMultipleChoiceExamDto> questionsMultipleChoice = examDto.getQuestionsMultipleChoice();
        for (QuestionMultipleChoiceExamDto question : questionsMultipleChoice) {
            List<AnswerExamDto> answers = question.getAnswers();
            boolean allCorrect = true;

            StudentQuestionPointsEntity studentQuestionPointsEntity = new StudentQuestionPointsEntity();
            studentQuestionPointsEntity.setIdStudent(idStudent);
            studentQuestionPointsEntity.setIdQuestion(question.getId());

            for(AnswerExamDto answer : answers){
                if(answer.isCorrect()){
                    // check if in correctAnswers there is not an answer with the same id
                    if(allCorrect && this.correctAnswerExamRepository.findByIdAnswer(answer.getId()).isEmpty()){
                        allCorrect = false;
                    }

                    // save the answer of the student
                    StudentMultipleChoiceResponsesEntity studentMultipleChoiceResponsesEntity = new StudentMultipleChoiceResponsesEntity();
                    studentMultipleChoiceResponsesEntity.setStudentQuestionPoints(studentQuestionPointsEntity);
                    studentMultipleChoiceResponsesEntity.setIdAnswer(answer.getId());

                    // add the answer to the list of answers of the student
                    studentQuestionPointsEntity.getStudentMultipleChoiceResponsesList()
                            .add(studentMultipleChoiceResponsesEntity);
                } else {
                    // check if in correctAnswers there is an answer with the same id
                    if(allCorrect && this.correctAnswerExamRepository.findByIdAnswer(answer.getId()).isPresent()){
                        allCorrect = false;
                    }
                }
            }

            double points = allCorrect ? question.getPoints() : 0.0;

            // save the points of the student
            studentQuestionPointsEntity.setPointsGiven(points);
            this.studentQuestionPointsRepository.save(studentQuestionPointsEntity);

            totalPoints += points;
        }

        // save the responses to the long response questions
        saveStudentLongResponses(examDto, idStudent);

        // save the total points of the student
        studentTookExamsEntity.setPointsPerExam(totalPoints);
        this.studentTookExamsRepository.save(studentTookExamsEntity);
    }

    private void saveStudentLongResponses(ExamDto examDto, Long idStudent) {
        // save the responses to the long response questions
        List<QuestionLongResponseExamDto> questionsLongResponse = examDto.getQuestionsLongResponse();
        for (QuestionLongResponseExamDto question : questionsLongResponse) {
            StudentQuestionPointsEntity studentQuestionPointsEntity = new StudentQuestionPointsEntity();

            studentQuestionPointsEntity.setIdStudent(idStudent);
            studentQuestionPointsEntity.setIdQuestion(question.getId());

            StudentLongResponsesEntity studentLongResponseEntity = new StudentLongResponsesEntity();
            studentLongResponseEntity.setStudentQuestionPoints(studentQuestionPointsEntity);
            studentLongResponseEntity.setStudentResponse(question.getExpectedResponse());

            studentQuestionPointsEntity.getStudentLongResponses().add(studentLongResponseEntity);

            studentQuestionPointsEntity.setPointsGiven((double) 0);

            this.studentQuestionPointsRepository.save(studentQuestionPointsEntity);
        }
    }



    private String generateCode() {
        StringBuilder alphabet = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        do{
            for(int i= 0; i < 6; i++) {
                sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
            }
        }while(this.examRepository.findByCode(sb.toString()).isPresent());


        return sb.toString();

    }


}
