package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.DidacticEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DidacticRepository extends JpaRepository<DidacticEntity, Long> {
    @Query(value = "SELECT * FROM DIDACTIC WHERE ID_PROFESSOR = :professorId AND ID_COURSE = :courseId",nativeQuery = true)
    DidacticEntity findByProfessorIdAndCourseId(@Param("professorId") Long professorId, @Param("courseId") Long courseId);
}
