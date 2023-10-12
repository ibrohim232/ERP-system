package com.example.erpsystem.service;

import com.example.erpsystem.dto.base.JwtResponse;
import com.example.erpsystem.dto.user.*;
import com.example.erpsystem.entity.UserEntity;
import com.example.erpsystem.entity.enums.Permissions;
import com.example.erpsystem.entity.enums.UserRole;
import com.example.erpsystem.exception.DataNotFoundException;
import com.example.erpsystem.exception.WrongInputException;
import com.example.erpsystem.repository.UserRepository;
import com.example.erpsystem.service.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;

    private ModelMapper modelMapper;
    @Mock
    private JavaMailSender javaMailSender;

    private UserService userService;


    private SingIdDto singIdDto;
    private JwtResponse jwtResponse;

    private UserEntity user;

    private UserRequestDto userRequestDto;

    private UserResponseDto userResponseDto;

    private UpdateUserRoleDto updateUserRoleDto;

    private UpdateUserPermissionsDto updateUserPermissionsDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
        userService = new UserService(userRepository, modelMapper, passwordEncoder, jwtService, javaMailSender);
    }

    @Test
    void singUp() {
        user = new UserEntity();
        user.setPassword("232");
        UserRequestDto createReq = new UserRequestDto();
        createReq.setEmail("232");
        Mockito.doNothing().when(javaMailSender).send(Mockito.any(SimpleMailMessage.class));
        Mockito.when(userRepository.save(user)).thenReturn(user);
        UserResponseDto userResponseDto1 = userService.singUp(createReq);
        assertNotNull(userResponseDto1);
    }

    @Test
    void singIn() {
        user = new UserEntity();
        user.setUserName("232");
        user.setPassword("232");
        user.setValidate(true);
        singIdDto = new SingIdDto();
        singIdDto.setUserName("232");
        singIdDto.setPassword("232");
        Mockito.when(userRepository.findByUserName(singIdDto.getUserName())).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(singIdDto.getPassword(), user.getPassword())).thenReturn(true);

        assertNotNull(userService.singIn(singIdDto));
    }

    @Test
    void verify() {
        UUID uuid = UUID.randomUUID();
        user = new UserEntity();
        user.setId(uuid);
        user.setCode(123);
        Mockito.when(userRepository.findByEmail("email")).thenReturn(Optional.of(user));
        JwtResponse email = userService.verify("email", 123);
        assertNotNull(email);
    }

    @Test
    void updateUserRole() {
        UUID uuid = UUID.randomUUID();
        user = new UserEntity();

        updateUserRoleDto = new UpdateUserRoleDto(uuid, UserRole.ADMIN);

        Mockito.when(userRepository.findById(uuid)).thenReturn(Optional.of(user));

        UserResponseDto userResponseDto1 = userService.updateUserRole(updateUserRoleDto);
        assertEquals(UserRole.ADMIN, userResponseDto1.getRole());
    }

    @Test
    void updateUserPermissions() {
        UUID uuid = UUID.randomUUID();
        user = new UserEntity();

        updateUserPermissionsDto = new UpdateUserPermissionsDto(uuid, List.of(Permissions.USER_GET));

        Mockito.when(userRepository.findById(uuid)).thenReturn(Optional.of(user));
        UserResponseDto userResponseDto1 = userService.updateUserPermissions(updateUserPermissionsDto);
        assertEquals(List.of(Permissions.USER_GET), userResponseDto1.getPermissions());
    }

    @Test
    void singUpFailEmailIsNull() {
        userRequestDto = new UserRequestDto();
        assertThrows(WrongInputException.class, () -> userService.singUp(userRequestDto));
    }

    @Test
    void singUpFailSendingMessage() {
        userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("232");
        assertThrows(WrongInputException.class, () -> userService.singUp(userRequestDto));
    }

    @Test
    void verifyFailIncorrectCode() {
        user = new UserEntity();
        user.setCode(223);
        Mockito.when(userRepository.findByEmail("email")).thenReturn(Optional.of(user));
        assertThrows(WrongInputException.class, () -> userService.verify("email", 123));
    }

    @Test
    void verifyFailEmailNotFound() {
        assertThrows(DataNotFoundException.class, () -> userService.verify("email", 123));
    }

    @Test
    void updateUserRoleFailUserNotFound() {
        updateUserRoleDto = new UpdateUserRoleDto();
        updateUserRoleDto.setUserId(UUID.randomUUID());
        assertThrows(DataNotFoundException.class, () -> userService.updateUserRole(updateUserRoleDto));
    }

    @Test
    void updateUserPermissionsFailUserNotFound() {
        updateUserPermissionsDto = new UpdateUserPermissionsDto();
        updateUserPermissionsDto.setUserId(UUID.randomUUID());
        assertThrows(DataNotFoundException.class, () -> userService.updateUserPermissions(updateUserPermissionsDto));
    }
}