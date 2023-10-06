package com.example.erpsystem.service;

import com.example.erpsystem.dto.base.JwtResponse;
import com.example.erpsystem.dto.user.*;
import com.example.erpsystem.entity.UserEntity;
import com.example.erpsystem.exception.DataNotFoundException;
import com.example.erpsystem.repository.UserRepository;
import com.example.erpsystem.service.jwt.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService extends BaseService<UserEntity, UUID, UserRepository, UserResponseDto, UserRequestDto> {
    public UserService(UserRepository repository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JwtService jwtService) {
        super(repository, modelMapper);
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    protected UserResponseDto mapEntityToRES(UserEntity entity) {
        return new UserResponseDto(
                entity.getId(),
                entity.getCreated(),
                entity.getUpdated(),
                entity.getFullName(),
                entity.getUsername(),
                entity.getPhoneNumber());
    }

    @Override
    protected UserEntity mapCRToEntity(UserRequestDto createReq) {
        UserEntity map = modelMapper.map(createReq, UserEntity.class);
        map.setPassword(passwordEncoder.encode(map.getPassword()));
        return map;
    }


    public JwtResponse singUp(UserRequestDto createReq) {
        UserEntity save = repository.save(mapCRToEntity(createReq));
        return new JwtResponse(jwtService.generateToken(save));
    }

    public JwtResponse singIn(SingIdDto singIdDto) {
        try {
            UserEntity userEntity = repository.findByUserName(singIdDto.getUserName()).orElseThrow();
            if (!passwordEncoder.matches(singIdDto.getPassword(), userEntity.getPassword()))
                throw new RuntimeException();
            return new JwtResponse(jwtService.generateToken(userEntity));
        } catch (Exception e) {
            throw new DataNotFoundException("Username or password incorrect");
        }
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
