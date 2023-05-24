package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.advice.exceptions.EntityAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.dtos.*;
import com.example.Neurosurgical.App.advice.exceptions.InvalidDateException;
import com.example.Neurosurgical.App.services.ExamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/exam")
public class ExamController {

    private final ExamService examService;

    @Autowired
    public ExamController(ExamService examService){
        this.examService = examService;
    }

    @PostMapping(value = "/create", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createExam(@RequestBody @Valid ExamCreationDto examCreationDto) throws InvalidDateException, EntityAlreadyExistsException {
        examService.createExam(examCreationDto);
        return ResponseEntity.noContent().build();
    }

    /**
     * This endpoints will be called after a student has finished an exam.
     *
     * @param examDto
     * @param idStudent
     * @return HttpStatus.CREATED if successful
     */
    @PostMapping(value = "/evaluate/idStudent={idStudent}", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> evaluateExam(@RequestBody @Valid ExamDto examDto, @PathVariable @Valid Long idStudent) {
        examService.evaluateExam(examDto, idStudent);
        return ResponseEntity.noContent().build();
    }

    /**
     * This endpoint will be called by the professor to evaluate a long response question from a student's exam.
     *
     * @param idStudent
     * @param idQuestion
     * @param points
     * @return
     */
    @PostMapping(value = "/evaluate/idStudent={idStudent}/idQuestion={idQuestion}", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> evaluateLongResponseQuestion(@PathVariable @Valid Long idStudent, @PathVariable @Valid Long idQuestion, @RequestBody @Valid Double points) {
        examService.evaluateLongResponseQuestion(idStudent, idQuestion, points);
        return ResponseEntity.noContent().build();
    }


     @GetMapping(value = "viewExamResult/idExam={idExam}/idStudent={idStudent}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ExamResultDto> viewExamResult(@PathVariable @Valid Long idExam, @PathVariable @Valid Long idStudent) {
        return ResponseEntity.ok(examService.viewExamResult(idExam, idStudent));
    }

    @GetMapping(value = "/students/idExam={idExam}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ExamStudentSummariseDto>> viewStudentExamSummarise(@PathVariable @Valid Long idExam) {
        return ResponseEntity.ok(examService.viewStudentExamSummarise(idExam));
    }

    @GetMapping(value = "/code={code}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ExamDto> findByCode(@PathVariable @Valid String code) {
        return ResponseEntity.ok(examService.findByCode(code));
    }

    @GetMapping(value = "/code={code}/idStudent={idStudent}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ExamDto> findByCodeForStudent(@PathVariable @Valid String code, @PathVariable @Valid Long idStudent) {
        try{
            return ResponseEntity.ok(examService.findByCodeForStudent(code,idStudent));
        }catch (EntityAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/summarise/idStudent={idStudent}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ExamSummariseDto>> findByIdStudent(@PathVariable @Valid Long idStudent) {
        return ResponseEntity.ok(examService.findByIdStudent(idStudent));
    }

    @GetMapping(value = "/summarise/idCourse={idCourse}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ExamSummariseDto>> findByIdCourse(@PathVariable @Valid Long idCourse) {
        return ResponseEntity.ok(examService.findByIdCourse(idCourse));
    }

    @GetMapping(value = "points/idStudent={idStudent}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ExamStudentPointsDto>> getPoints(@PathVariable @Valid Long idStudent) {
        return ResponseEntity.ok(examService.getPoints(idStudent));
    }

    @PostMapping(value = "activate/idExam={idExam}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> activateExam(@PathVariable @Valid Long idExam) {
        examService.activateExam(idExam);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "deactivate/idExam={idExam}", produces = "application/json")
    public ResponseEntity<Void> deactivateExam(@PathVariable @Valid Long idExam) {
        examService.deactivateExam(idExam);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/idExam={idExam}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExam(@PathVariable @Valid Long idExam) {
        examService.deleteExam(idExam);
    }

    @PutMapping(value = "/update/idExam={idExam}", consumes = "application/json")
    public ResponseEntity<Void> updateExam(@PathVariable @Valid Long idExam, @RequestBody @Valid ExamSummariseUpdateDto examSummariseUpdateDto) throws InvalidDateException {
        examService.updateExam(idExam, examSummariseUpdateDto);
        return ResponseEntity.noContent().build();
    }


}
