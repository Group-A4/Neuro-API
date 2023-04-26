package com.example.Neurosurgical.App.controllers;

import com.example.Neurosurgical.App.models.dtos.StudentCreationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional

public class StudentControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll_shouldReturnOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/students"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void getById_shouldReturnOk() throws Exception {
        Long id = 6L;
        mockMvc.perform(MockMvcRequestBuilders.get("/students/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void getById_shouldReturn_BadRequest() throws Exception {
        Long id = 1000L;
        mockMvc.perform(MockMvcRequestBuilders.get("/students/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void getByCode_shouldReturnOk() throws Exception {
        String code = "52125-795";

        mockMvc.perform(MockMvcRequestBuilders.get("/students/code/{code}",code))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getByCode_shouldReturn_BadRequest() throws Exception {
        String code = "NotFound";

        mockMvc.perform(MockMvcRequestBuilders.get("/students/code={code}",code))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Rollback
    void deleteUserById_shouldReturnOk() throws Exception {
        Long id = 6L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/students/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteUserById_shouldReturn_isNotFound() throws Exception {
        Long id = 1000L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/students/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void createStudent_shouldReturn_BadRequest() throws Exception {
        StudentCreationDto studentCreationDto = StudentCreationDto.builder()
                .firstName("Jack")
                .lastName("Wilson")
                .emailFaculty("rbeldon3@umfiasi.ro")
                .emailPersonal("rbeldon3@umfiasi.ro")
                .year(Integer.parseInt("1"))
                .semester(Integer.parseInt("1"))
                .birthDate(Timestamp.valueOf("2000-06-11 00:00:00"))
                .password("674")
                .code("def")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/students/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(studentCreationDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Rollback
    void createStudent_shouldReturnOk() throws Exception {
        StudentCreationDto studentCreationDto = StudentCreationDto.builder()
                .firstName("Brian")
                .lastName("Wilson")
                .emailFaculty("BrianWilson@gmail.com")
                .emailPersonal("BrianWilson@gmail.com")
                .year(Integer.parseInt("1"))
                .semester(Integer.parseInt("1"))
                .birthDate(Timestamp.valueOf("2000-06-11 00:00:00"))
                .password("abc")
                .code("567")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/students/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentCreationDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateStudent_shouldReturn_BadRequest() throws Exception {
        Long id = 1000L;
        mockMvc.perform(MockMvcRequestBuilders.put("/students/update/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Rollback
    void updateStudent_shouldReturnOk() throws Exception {
        Long id = 6L;
        StudentCreationDto studentCreationDto = StudentCreationDto.builder()
                .firstName("Jack")
                .lastName("Evans")
                .emailFaculty("j@gmail.com")
                .emailPersonal("j@gmail.com")
                .year(Integer.parseInt("1"))
                .semester(Integer.parseInt("2"))
                .birthDate(Timestamp.valueOf("2000-06-11 00:00:00"))
                .password("567")
                .code("126")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/students/update/{id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(studentCreationDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findByCourseId_shouldReturnOk() throws Exception {
        Long courseId = 4L;

        mockMvc.perform(MockMvcRequestBuilders.get("/students/course={id}",courseId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    void findByCourseId_shouldReturn_BadRequest() throws Exception {
        Long courseId = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.get("/students/course={id}",courseId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


}
