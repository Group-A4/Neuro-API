package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.dtos.UserStudentDTO;
import com.example.Neurosurgical.App.models.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
