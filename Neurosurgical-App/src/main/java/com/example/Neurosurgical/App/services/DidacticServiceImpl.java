package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityAlreadyExistsException;
import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.models.entities.CourseEntity;
import com.example.Neurosurgical.App.models.entities.DidacticEntity;
import com.example.Neurosurgical.App.models.entities.ProfessorEntity;
import com.example.Neurosurgical.App.repositories.CourseRepository;
import com.example.Neurosurgical.App.repositories.DidacticRepository;
import com.example.Neurosurgical.App.repositories.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DidacticServiceImpl implements DidacticService{
    private final DidacticRepository didacticRepository;
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;

    public DidacticServiceImpl(DidacticRepository didacticRepository, CourseRepository courseRepository, ProfessorRepository professorRepository) {
        this.didacticRepository = didacticRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    public void deleteDidactic(Long id){
        Optional<DidacticEntity> didacticEntityOptional = didacticRepository.findById(id);
        if (didacticEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Didactic not found");
        }
        didacticRepository.delete(didacticEntityOptional.get());
    }

    @Override
    public void createDidactic(Long courseId, Long professorId){
        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(courseId);
        Optional<ProfessorEntity> professorEntityOptional = professorRepository.findById(professorId);

        if (courseEntityOptional.isEmpty() || professorEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Course or professor not found");
        }

        DidacticEntity didacticEntity = new DidacticEntity( professorEntityOptional.get(), courseEntityOptional.get());
        try{
            didacticRepository.save(didacticEntity);
        }catch (Exception e){
            throw new EntityAlreadyExistsException("Didactic", "Didactic");
        }
    }

    @Override
    public void deleteDidactic(Long courseId, Long professorId){
        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(courseId);
        Optional<ProfessorEntity> professorEntityOptional = professorRepository.findById(professorId);

        if (courseEntityOptional.isEmpty() || professorEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Course or professor not found");
        }
        didacticRepository.delete(didacticRepository.findByProfessorIdAndCourseId(professorId, courseId));
    }
}
