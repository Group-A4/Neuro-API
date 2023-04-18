package com.example.Neurosurgical.App.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfessorControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void getAll_shouldReturns_isOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/professors"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void findById_shouldReturns_isOk() throws Exception {

            Long id = 52L;

            mockMvc.perform(MockMvcRequestBuilders.get("/professors/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void findById_shouldReturns_notFound() throws Exception {
                Long id = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.get("/professors/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    void findByCode_shouldReturns_isOk() throws Exception {
        String code = "36800-079";

        mockMvc.perform(MockMvcRequestBuilders.get("/professors/code/{code}",code))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void findByCode_shouldReturns_notFound() throws Exception {
            String code = "NotFound";

        mockMvc.perform(MockMvcRequestBuilders.get("/professors/code={code}",code))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void findByCourseId_shouldReturns_isOk() throws Exception {
        Long courseId = 3L;

        mockMvc.perform(MockMvcRequestBuilders.get("/professors/course={id}",courseId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    void findByCourseId_shouldReturns_notFound() throws Exception {
        Long courseId = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.get("/professors/course={id}",courseId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void findByMateriaId_shouldReturns_isOk() throws Exception {
        Long materialId = 5L;

        mockMvc.perform(MockMvcRequestBuilders.get("/professors/material={id}",materialId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    void findByMateriaId_shouldReturns_notFound() throws Exception {
        Long materialId = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.get("/professors/material={id}",materialId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
