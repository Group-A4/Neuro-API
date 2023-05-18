package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.LectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LectureRepository extends JpaRepository<LectureEntity, Long> {
    @Query(value = "SELECT * FROM lectures l WHERE l.title = :title",nativeQuery = true)
    Optional<LectureEntity> findByTitle(@Param(value = "title")String  title);

    @Query(value = "SELECT * FROM lectures l WHERE l.id_course = :id",nativeQuery = true)
    List<LectureEntity> findAllByCourseId(@Param(value = "id")Long id);
}
