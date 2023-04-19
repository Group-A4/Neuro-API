package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.StudentFollowsCoursesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface StudentFollowsCoursesRepository extends JpaRepository<StudentFollowsCoursesEntity, Long> {
    @Query(value = "SELECT * FROM STUDENT_FOLLOWS_COURSES WHERE ID_STUDENT = :studentId AND ID_COURSE = :courseId",nativeQuery = true)
    StudentFollowsCoursesEntity findByStudentIdAndCourseId(@Param("studentId") Long studentId,@Param("courseId") Long courseId);
}
