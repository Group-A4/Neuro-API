package com.example.Neurosurgical.App.dao;

import com.example.Neurosurgical.App.model.entity.StudentEntity;
import com.example.Neurosurgical.App.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao extends JpaRepository<StudentEntity,Long> {
    @Query(value = "SELECT * FROM STUDENTS WHERE code = :code",nativeQuery = true)
    StudentEntity findByCode(@Param("code") String code);
}
