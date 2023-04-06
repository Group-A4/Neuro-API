package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<ProfessorEntity, Long> {

}
