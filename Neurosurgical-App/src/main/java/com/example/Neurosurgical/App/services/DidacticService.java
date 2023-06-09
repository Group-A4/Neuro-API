package com.example.Neurosurgical.App.services;

import org.springframework.stereotype.Service;

@Service
public interface DidacticService {
    void deleteDidactic(Long id);
    void createDidactic(Long courseId, Long professorId);
    void deleteDidactic(Long courseId, Long professorId);
}
