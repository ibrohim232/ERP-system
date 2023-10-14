package com.example.erpsystem.controller;

import com.example.erpsystem.ErpSystemApplication;
import com.example.erpsystem.dto.base.JwtResponse;
import com.example.erpsystem.dto.user.*;
import com.example.erpsystem.entity.enums.Permissions;
import com.example.erpsystem.entity.enums.UserRole;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ErpSystemApplication.class)
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void singUp() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        UserRequestDto userRequestDto = new UserRequestDto("232", "232", "232", "232", "nabiyevibrohim989@gmial.com");
        MvcResult result = mockMvc.perform(post("/sign-up").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userRequestDto))).andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        UserResponseDto userResponseDto = objectMapper.readValue(contentAsString, UserResponseDto.class);
        assertNotNull(userResponseDto);
        assertNotNull(userResponseDto.getId());

    }

    @Test
    void singIn() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        SingIdDto singIdDto = new SingIdDto("232", "232");
        MvcResult result = mockMvc.perform(post("/sign-in").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(singIdDto))).andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        JwtResponse jwtResponse = objectMapper.readValue(contentAsString, JwtResponse.class);
        assertNotNull(jwtResponse);
    }

    @Test
    void verify() throws Exception {
        objectMapper.registerModule(new JavaTimeModule());
        MvcResult result = mockMvc.perform(post("/verify").param("email", "nabiyevibrohim989@gmial.com").param("code", "556")).andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        JwtResponse jwtResponse = objectMapper.readValue(contentAsString, JwtResponse.class);
        assertNotNull(jwtResponse);
    }

    @Test
    void getAll() throws Exception {
        SingIdDto singIdDto = new SingIdDto("232", "232");
        MvcResult result = mockMvc.perform(post("/sign-in").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(singIdDto))).andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        JwtResponse jwtResponse = objectMapper.readValue(contentAsString, JwtResponse.class);
        objectMapper.registerModule(new JavaTimeModule());
        MvcResult resultGet = mockMvc.perform(MockMvcRequestBuilders.get("/get-all").header("authorization", "Bearer " + jwtResponse.getToken()).param("size", "10").param("page", "0")).andReturn();
        String content = resultGet.getResponse().getContentAsString();
        List<UserResponseDto> userResponseDtos = objectMapper.readValue(content, new TypeReference<List<UserResponseDto>>() {
        });

        assertNotNull(userResponseDtos);
    }

    @Test
    void changeRole() throws Exception {
        SingIdDto singIdDto = new SingIdDto("232", "232");
        MvcResult result = mockMvc.perform(
                post("/sign-in")
                        .contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(singIdDto))
        ).andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        JwtResponse jwtResponse = objectMapper.readValue(contentAsString, JwtResponse.class);
        objectMapper.registerModule(new JavaTimeModule());
        UpdateUserRoleDto updateUserRoleDto =
                new UpdateUserRoleDto(UUID.fromString("ccdee66d-c8ac-4a67-82b4-c42d5bae26ad"), UserRole.ADMIN);
        MvcResult resultGet = mockMvc.perform(
                        MockMvcRequestBuilders.post("/change-role")
                                .header("authorization", "Bearer " + jwtResponse.getToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateUserRoleDto)))
                .andReturn();
        String content = resultGet.getResponse().getContentAsString();
        UserResponseDto userResponseDtos = objectMapper.readValue(content, UserResponseDto.class);

        assertEquals(UserRole.ADMIN, userResponseDtos.getRole());
    }

    @Test
    void changePermissions() throws Exception {
        SingIdDto singIdDto = new SingIdDto("232", "232");
        MvcResult result = mockMvc.perform(
                post("/sign-in")
                        .contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(singIdDto))
        ).andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        JwtResponse jwtResponse = objectMapper.readValue(contentAsString, JwtResponse.class);
        objectMapper.registerModule(new JavaTimeModule());
        UpdateUserPermissionsDto updateUserRoleDto =
                new UpdateUserPermissionsDto(UUID.fromString("ccdee66d-c8ac-4a67-82b4-c42d5bae26ad"),List.of(Permissions.LESSON_UPDATE,Permissions.LESSON_GET));
        MvcResult resultGet = mockMvc.perform(
                        MockMvcRequestBuilders.post("/change-permissions")
                                .header("authorization", "Bearer " + jwtResponse.getToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateUserRoleDto)))
                .andReturn();
        String content = resultGet.getResponse().getContentAsString();
        UserResponseDto userResponseDtos = objectMapper.readValue(content, UserResponseDto.class);

        assertNotNull(userResponseDtos.getPermissions());
    }


}