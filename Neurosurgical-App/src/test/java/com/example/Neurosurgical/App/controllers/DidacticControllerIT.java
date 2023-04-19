package com.example.Neurosurgical.App.controllers;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DidacticControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void deleteDidacticById_shouldReturnBadRequest() throws Exception {
        Long id = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/didactic/delete/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteDidacticByCourseIdAndProfessorId_shouldReturnNotFound() throws Exception {
        Long courseId = 1000L;
        Long professorId = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/didactic/delete/course={courseId}/professor={professorId}", courseId, professorId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void createDidacticByCourseIdAndProfessorId_shouldReturnNotFound() throws Exception {
        Long courseId = 1000L;
        Long professorId = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.post("/didactic/create/course={courseId}/professor={professorId}", courseId, professorId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Rollback
    void deleteDidacticById_shouldReturnOk() throws Exception {
        Long id = 2L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/didactic/delete/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Rollback
    void createDidacticByCourseIdAndProfessorId_shouldReturnOk() throws Exception {
        Long courseId = 4L;
        Long professorId = 58L;

        mockMvc.perform(MockMvcRequestBuilders.post("/didactic/create/course={courseId}/professor={professorId}", courseId, professorId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Rollback
    void deleteDidacticByCourseIdAndProfessorId_shouldReturnOk() throws Exception {
        Long courseId = 4L;
        Long professorId = 60L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/didactic/delete/course={courseId}/professor={professorId}", courseId, professorId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
