package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.models.dtos.ProfessorCreationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class ProfessorControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void updateProfessor_shouldReturnBadRequest() throws Exception {
        Long id = 1000L;
        mockMvc.perform(MockMvcRequestBuilders.put("/professors/update/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deleteProfessor_shouldReturnNotFound() throws Exception {
        Long id = 1000L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/professors/delete/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void createProfessor_shouldReturnBadRequest() throws Exception {
        ProfessorCreationDto professorCreationDto = ProfessorCreationDto.builder()
                .firstName("John")
                .lastName("Doe")
                .code("string")
                .emailFaculty("john@gmail.com")
                .emailPersonal("john@gmail.com")
                .degree("PhD")
                .password("123")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/professors/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(professorCreationDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Rollback
    void createProfessor_shouldReturnOk() throws Exception {
        ProfessorCreationDto professorCreationDto = ProfessorCreationDto.builder()
                .firstName("John")
                .lastName("Doe")
                .code("777")
                .emailFaculty("k@gmail.com")
                .emailPersonal("k@gmail.com")
                .degree("PhD")
                .password("123")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/professors/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(professorCreationDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Rollback
    void deleteProfessor_shouldReturnOk() throws Exception {
        Long id = 52L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/professors/delete/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Rollback
    void updateProfessor_shouldReturnOk() throws Exception {
        Long id = 85L;
        ProfessorCreationDto professorCreationDto = ProfessorCreationDto.builder()
                .firstName("John")
                .lastName("Doe")
                .code("99999")
                .emailFaculty("j@m.com")
                .emailPersonal("")
                .degree("PhD")
                .password("123")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/professors/update/{id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(professorCreationDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
