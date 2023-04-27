package com.example.Neurosurgical.App.repository;

import com.example.Neurosurgical.App.model.entity.ExamAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamAnswerRepository extends JpaRepository<ExamAnswerEntity, Long> {
}
