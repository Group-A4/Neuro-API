package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
