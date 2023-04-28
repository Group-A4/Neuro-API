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
    void deleteDidacticById_shouldReturn_BadRequest() throws Exception {
        Long id = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/didactic/delete/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deleteDidacticByCourseIdAndProfessorId_shouldReturn_BadRequest() throws Exception {
        Long courseId = 1000L;
        Long professorId = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/didactic/delete/course={courseId}/professor={professorId}", courseId, professorId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createDidacticByCourseIdAndProfessorId_shouldReturn_BadRequest() throws Exception {
        Long courseId = 1000L;
        Long professorId = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.post("/didactic/create/course={courseId}/professor={professorId}", courseId, professorId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Rollback
    void deleteDidacticById_shouldReturn_isOk() throws Exception {
        Long id = 2L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/didactic/delete/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Rollback
    void createDidacticByCourseIdAndProfessorId_shouldReturn_isOk() throws Exception {
        Long courseId = 4L;
        Long professorId = 58L;

        mockMvc.perform(MockMvcRequestBuilders.post("/didactic/create/course={courseId}/professor={professorId}", courseId, professorId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Rollback
    void deleteDidacticByCourseIdAndProfessorId_shouldReturn_isOk() throws Exception {
        Long courseId = 4L;
        Long professorId = 60L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/didactic/delete/course={courseId}/professor={professorId}", courseId, professorId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
