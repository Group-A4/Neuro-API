package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.QuestionExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionExamRepository extends JpaRepository<QuestionExamEntity, Long>{

}
