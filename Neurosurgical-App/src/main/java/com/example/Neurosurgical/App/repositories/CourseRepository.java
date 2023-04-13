package com.example.Neurosurgical.App.repositories;


import com.example.Neurosurgical.App.models.dtos.CourseDto;
import com.example.Neurosurgical.App.models.entities.CourseEntity;
import com.example.Neurosurgical.App.models.entities.StudentEntity;
import jakarta.persistence.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity,Long> {

    @Query(value = "SELECT * FROM courses WHERE code = :code",nativeQuery = true)
    CourseEntity findByCode(@Param("code") String code);

    @Query(value = "SELECT * FROM courses WHERE title = :title",nativeQuery = true)
    CourseEntity findByTitle(@Param("title") String title);

}