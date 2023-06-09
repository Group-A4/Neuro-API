package com.example.Neurosurgical.App.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.Neurosurgical.App.models.dtos.ProfessorCreationDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.annotation.Rollback;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProfessorControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void updateProfessor_shouldReturn_BadRequest() throws Exception {
        Long id = 1000L;
        mockMvc.perform(MockMvcRequestBuilders.put("/professors/update/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deleteProfessor_shouldReturn_BadRequest() throws Exception {
        Long id = 1000L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/professors/delete/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createProfessor_shouldReturn_BadRequest() throws Exception {
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
    void createProfessor_shouldReturn_isOk() throws Exception {
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
    void deleteProfessor_shouldReturn_isOk() throws Exception {
        Long id = 52L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/professors/delete/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Rollback
    void updateProfessor_shouldReturn_isOk() throws Exception {
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
    void findById_shouldReturns_BadRequest() throws Exception {
                Long id = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.get("/professors/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void findByCode_shouldReturns_isOk() throws Exception {
        String code = "36800-079";

        mockMvc.perform(MockMvcRequestBuilders.get("/professors/code/{code}",code))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void findByCode_shouldReturns_BadRequest() throws Exception {
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
    void findByCourseId_shouldReturns_BadRequest() throws Exception {
        Long courseId = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.get("/professors/course={id}",courseId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void findByMaterialId_shouldReturns_isOk() throws Exception {
        Long materialId = 5L;

        mockMvc.perform(MockMvcRequestBuilders.get("/professors/material={id}",materialId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    void findByMaterialId_shouldReturns_BadRequest() throws Exception {
        Long materialId = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.get("/professors/material={id}",materialId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
