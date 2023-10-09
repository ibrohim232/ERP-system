package com.example.erpsystem.service;

import com.example.erpsystem.dto.base.JwtResponse;
import com.example.erpsystem.dto.user.*;
import com.example.erpsystem.entity.UserEntity;
import com.example.erpsystem.exception.DataNotFoundException;
import com.example.erpsystem.exception.WrongInputException;
import com.example.erpsystem.repository.UserRepository;
import com.example.erpsystem.service.jwt.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class UserService extends BaseService<UserEntity, UUID, UserRepository, UserResponseDto, UserRequestDto> {
    public UserService(UserRepository repository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JwtService jwtService, JavaMailSender javaMailSender) {
        super(repository, modelMapper);
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.javaMailSender = javaMailSender;
    }

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JavaMailSender javaMailSender;
    private final Random random = new Random();
    @Value("${spring.mail.username}")
    private String sender;

    @Override
    protected UserResponseDto mapEntityToRES(UserEntity entity) {
        return new UserResponseDto(
                entity.getId(),
                entity.getCreated(),
                entity.getUpdated(),
                entity.getFullName(),
                entity.getUsername(),
                entity.getPhoneNumber(), entity.getRole(), entity.getPermissions());
    }

    @Override
    protected UserEntity mapCRToEntity(UserRequestDto createReq) {
        UserEntity map = modelMapper.map(createReq, UserEntity.class);
        map.setPassword(passwordEncoder.encode(map.getPassword()));
        return map;
    }


    public UserResponseDto singUp(UserRequestDto createReq) {
        UserEntity user = mapCRToEntity(createReq);
        user.setCode(random.nextInt(100, 1000));

        if (createReq.getEmail() == null) {
            throw new WrongInputException("email is null");
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(createReq.getEmail());
            message.setText(String.valueOf(user.getCode()));
            javaMailSender.send(message);
            return mapEntityToRES(repository.save(user));
        } catch (Exception e) {
            throw new WrongInputException(e.getMessage());
        }
    }

    public JwtResponse singIn(SingIdDto singIdDto) {
        try {
            UserEntity userEntity = repository.findByUserName(singIdDto.getUserName()).orElseThrow(() -> new DataNotFoundException("user not found"));
            if (!userEntity.isValidate()) {
                throw new WrongInputException("isValidate is false");
            }
            if (!passwordEncoder.matches(singIdDto.getPassword(), userEntity.getPassword()))
                throw new RuntimeException();
            return new JwtResponse(jwtService.generateToken(userEntity));
        } catch (Exception e) {
            throw new WrongInputException("Username or password incorrect");
        }
    }

    public JwtResponse checkCode(String email, int code) {
        UserEntity user = repository.findByEmail(email).orElseThrow(() -> new DataNotFoundException("user not found"));
        if (user.getCode() == code) {
            user.setValidate(true);
            repository.save(user);
            return new JwtResponse(jwtService.generateToken(user));
        }
        throw new WrongInputException("incorrect code");
    }

    public UserResponseDto updateUserRole(UpdateUserRoleDto dto) {
        UserEntity user = repository.findById(dto.getUserId()).orElseThrow(() -> new DataNotFoundException("user not found"));
        user.setRole(dto.getRole());
        repository.save(user);
        return mapEntityToRES(user);
    }

    public UserResponseDto updateUserPermissions(UpdateUserPermissionsDto dto) {
        UserEntity user = repository.findById(dto.getUserId()).orElseThrow(() -> new DataNotFoundException("user not found"));
        user.setPermissions(dto.getPermissions());
        repository.save(user);
        return mapEntityToRES(user);
    }


}
