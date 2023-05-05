package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.ExamEntity;
import com.example.Neurosurgical.App.models.entities.ExamQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ExamRepository extends JpaRepository<ExamEntity, Long> {
    /*@Query(value = "SELECT * FROM exam WHERE id_course = :idCourse",nativeQuery = true)
    Optional<List<ExamEntity>> findByIdCourse(@Param("idCourse") Long id);*/

    @Query(value = "SELECT id FROM exam WHERE id_course = :idCourse",nativeQuery = true)
    Optional<ArrayList<Long>> findByIdCourse(@Param("idCourse") Long id);
}
