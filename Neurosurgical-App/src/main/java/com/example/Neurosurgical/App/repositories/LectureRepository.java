package com.example.Neurosurgical.App.repositories;

import com.example.Neurosurgical.App.models.entities.LectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<LectureEntity, Long> {
}
