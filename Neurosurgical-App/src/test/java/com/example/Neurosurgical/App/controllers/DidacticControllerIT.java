package com.example.Neurosurgical.App.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class DidacticControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void deleteDidacticById_shouldReturnBadRequest() throws Exception {
        Long id = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/didactic/delete/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deleteDidacticByCourseIdAndProfessorId_shouldReturnBadRequest() throws Exception {
        Long courseId = 1000L;
        Long professorId = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/didactic/delete/course={courseId}/professor={professorId}", courseId, professorId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createDidacticByCourseIdAndProfessorId_shouldReturnBadRequest() throws Exception {
        Long courseId = 1000L;
        Long professorId = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.post("/didactic/create/course={courseId}/professor={professorId}", courseId, professorId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createDidacticByCourseIdAndProfessorId_shouldReturnOk() throws Exception {
        Long courseId = 7L;
        Long professorId = 60L;

        mockMvc.perform(MockMvcRequestBuilders.post("/didactic/create/course={courseId}/professor={professorId}", courseId, professorId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteDidacticByCourseIdAndProfessorId_shouldReturnOk() throws Exception {
        Long courseId = 7L;
        Long professorId = 60L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/didactic/delete/course={courseId}/professor={professorId}", courseId, professorId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
