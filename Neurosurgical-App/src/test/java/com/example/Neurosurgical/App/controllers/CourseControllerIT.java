package com.example.Neurosurgical.App.controllers;


import com.example.Neurosurgical.App.models.dtos.CourseCreationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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
class CourseControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll_shouldReturnListOfCourses() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/courses"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getById_1_shouldReturnCourseById_shouldReturnOk() throws Exception {

        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/{id}", 3L))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getById_1000_shouldReturnCourseById_shouldReturnNotFound() throws Exception {

        Long id = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deleteCourseById_shouldDeleteCourse() throws Exception {

        Long id = 1000L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/courses/9"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createCourse_shouldCreateNewCourse_shouldReturnBadRequest() throws Exception {

        CourseCreationDto courseCreationDto = CourseCreationDto.builder()
                .title("SpringBootTest12")
                .year(1)
                .semester(1)
                .code("Spring_Boot_Test12")
                .credits(4)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/courses/create")
                        .content(objectMapper.writeValueAsString(courseCreationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void getByTitle_shouldReturnCourseByTitle_ShouldReturnOk() throws Exception {

        String title = "SpringBootTest12";

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/title={title}", title))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getByTitle_shouldReturnCourseByTitle_shouldReturnBadRequest() throws Exception {

        String title = "ExpectedBadRequest";

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/title={title}", title))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void getByCode_shouldReturnCourseByCode_ShouldReturnOk() throws Exception {

        String code = "Spring_Boot_Test12";

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/code={code}", code))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getByCode_shouldReturnCourseByCode_shouldReturnBadRequest() throws Exception {

        String code = "Expected_Bad_Request";

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/code={code}", code))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }



    @Test
    void updateCourse_shouldReturnBadRequest() throws Exception {

        Long id = 1000L;

        CourseCreationDto courseCreationDto = CourseCreationDto.builder()
                .title("SpringBootTestUpdate12")
                .year(1)
                .semester(1)
                .code("Spring_Boot_Test_Update12")
                .credits(4)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/courses/update/{id}",id)
                        .content(objectMapper.writeValueAsString(courseCreationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}