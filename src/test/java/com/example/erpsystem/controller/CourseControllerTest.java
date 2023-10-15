package com.example.erpsystem.controller;

import com.example.erpsystem.ErpSystemApplication;
import com.example.erpsystem.dto.base.JwtResponse;
import com.example.erpsystem.dto.course.CourseRequestDto;
import com.example.erpsystem.dto.course.CourseResponseDto;
import com.example.erpsystem.dto.user.SingIdDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ErpSystemApplication.class)
@AutoConfigureMockMvc
class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void save() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        SingIdDto singIdDto = new SingIdDto("232", "232");
        MvcResult result = mockMvc.perform(
                post("/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(singIdDto))
        ).andReturn();
        String jwt = result.getResponse().getContentAsString();
        JwtResponse jwtResponse = objectMapper.readValue(jwt, JwtResponse.class);
        CourseRequestDto courseRequestDto = new CourseRequestDto("php", 9);
        MvcResult mvcResult = mockMvc.perform(
                post("/course/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequestDto))
                        .header("authorization", "Bearer " + jwtResponse.getToken())
        ).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        CourseResponseDto courseResponseDto = objectMapper.readValue(contentAsString, CourseResponseDto.class);
        assertNotNull(courseResponseDto);
        assertNotNull(courseResponseDto.getId());
    }

    @Test
    void get() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        SingIdDto singIdDto = new SingIdDto("232", "232");
        MvcResult result = mockMvc.perform(
                post("/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(singIdDto))
        ).andReturn();
        String jwt = result.getResponse().getContentAsString();
        JwtResponse jwtResponse = objectMapper.readValue(jwt, JwtResponse.class);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/course/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("courseId", "b04a55a5-9b20-4b38-87f9-621dd15cabf5")
                        .header("authorization", "Bearer " + jwtResponse.getToken())
        ).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        CourseResponseDto courseResponseDto = objectMapper.readValue(contentAsString, CourseResponseDto.class);
        assertNotNull(courseResponseDto);
    }

    @Test
    void update() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        SingIdDto singIdDto = new SingIdDto("232", "232");
        MvcResult result = mockMvc.perform(
                post("/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(singIdDto))
        ).andReturn();
        String jwt = result.getResponse().getContentAsString();
        JwtResponse jwtResponse = objectMapper.readValue(jwt, JwtResponse.class);
        CourseRequestDto courseRequestDto = new CourseRequestDto("Java", 10);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/course/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("courseId", "b04a55a5-9b20-4b38-87f9-621dd15cabf5")
                        .content(objectMapper.writeValueAsString(courseRequestDto))
                        .header("authorization", "Bearer " + jwtResponse.getToken())
        ).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        CourseResponseDto courseResponseDto = objectMapper.readValue(contentAsString, CourseResponseDto.class);
        assertEquals(courseResponseDto.getCourseName(), courseResponseDto.getCourseName());
    }

    @Test
    void delete() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        SingIdDto singIdDto = new SingIdDto("232", "232");
        MvcResult result = mockMvc.perform(
                post("/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(singIdDto))
        ).andReturn();
        String jwt = result.getResponse().getContentAsString();
        JwtResponse jwtResponse = objectMapper.readValue(jwt, JwtResponse.class);

        assertDoesNotThrow(() -> mockMvc.perform(
                MockMvcRequestBuilders.get("/course/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("courseId", "b04a55a5-9b20-4b38-87f9-621dd15cabf5")
                        .header("authorization", "Bearer " + jwtResponse.getToken())
        ).andReturn());
    }

    @Test
    void getAll() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        SingIdDto singIdDto = new SingIdDto("232", "232");
        MvcResult result = mockMvc.perform(
                post("/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(singIdDto))
        ).andReturn();
        String jwt = result.getResponse().getContentAsString();
        JwtResponse jwtResponse = objectMapper.readValue(jwt, JwtResponse.class);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/course/get-all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("pageNumber", "0")
                        .param("size", "10")
                        .header("authorization", "Bearer " + jwtResponse.getToken())
        ).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<CourseResponseDto> courseResponseDto = objectMapper.readValue(contentAsString, new TypeReference<List<CourseResponseDto>>() {
        });
        assertNotNull(courseResponseDto);
    }
}