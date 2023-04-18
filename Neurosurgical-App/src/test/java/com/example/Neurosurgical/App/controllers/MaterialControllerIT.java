package com.example.Neurosurgical.App.controllers;


import com.example.Neurosurgical.App.models.dtos.CourseCreationDto;
import com.example.Neurosurgical.App.models.dtos.MaterialCreationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MaterialControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll_shouldReturnListOfMaterials() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/materials"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getById_1_shouldReturnMaterialById_shouldReturnOk() throws Exception {

        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/materials/{id}", 3L))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getById_1000_shouldReturnMaterialById_shouldReturnNotFound() throws Exception {

        Long id = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.get("/materials/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteMaterialById_shouldDeleteMaterial() throws Exception {

        Long id = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/materials/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createMaterial_shouldCreateNewMaterial_shouldReturnNotFound() throws Exception {

        MaterialCreationDto materialCreationDto = MaterialCreationDto.builder()
                .title("SpringBootTest14")
                .idCourse(1L)
                .idProfessor(1L)
                .link("www.doc1.com")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/materials/create")
                        .content(objectMapper.writeValueAsString(materialCreationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    @Rollback
    void createMaterial_shouldCreateNewMaterial_shouldReturnOk() throws Exception {

        Long courseId = 3L;
        Long idProfessor = 52L;

        MaterialCreationDto materialCreationDto = MaterialCreationDto.builder()
                .title("SpringBootTest14")
                .idCourse(courseId)
                .idProfessor(idProfessor)
                .link("www.doc1.com")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/materials/create")
                        .content(objectMapper.writeValueAsString(materialCreationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getByTitle_shouldReturnMaterialByTitle_ShouldReturnOk() throws Exception {

        String title = "Anatomie";

        mockMvc.perform(MockMvcRequestBuilders.get("/materials/title={title}", title))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllByCourseId_shouldReturnAllMaterialsByCourseId_shouldReturnOk() throws Exception {

        Long id = 2L;

        mockMvc.perform(MockMvcRequestBuilders.get("/materials/course={id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllByCourseId_shouldReturnAllMaterialsByCourseId_shouldReturnNotFound() throws Exception {

        Long id = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.get("/materials/course={id}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getAllByTeacherId_shouldReturnAllMaterialsByTeacherId_shouldReturnOk() throws Exception {

        Long id = 53L;

        mockMvc.perform(MockMvcRequestBuilders.get("/materials/teacher={id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllByTeacherId_shouldReturnAllMaterialsByTeacherId_shouldReturnNotFound() throws Exception {

        Long id = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.get("/materials/teacher={id}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getByTitle_shouldReturnMaterialByTitle_shouldReturnNotFound() throws Exception {

        String title = "ExpectedBadRequest";

        mockMvc.perform(MockMvcRequestBuilders.get("/materials/title={title}", title))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateMaterial_shouldReturnNotFound() throws Exception {

        Long id = 1000L;

        MaterialCreationDto materialCreationDto = MaterialCreationDto.builder()
                .title("SpringBootTestUpdate13")
                .idCourse(1L)
                .idProfessor(1L)
                .link("www.doc1.com")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/materials/update/{id}", id)
                        .content(objectMapper.writeValueAsString(materialCreationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Rollback
    void updateMaterial_shouldReturnOk() throws Exception {
        Long id = 1L;
        Long courseId = 3L;
        Long idProfessor = 52L;


        MaterialCreationDto materialCreationDto = MaterialCreationDto.builder()
                .title("SpringBootTestUpdate13")
                .idCourse(courseId)
                .idProfessor(idProfessor)
                .link("www.doc1.com")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/materials/update/{id}", id)
                        .content(objectMapper.writeValueAsString(materialCreationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}