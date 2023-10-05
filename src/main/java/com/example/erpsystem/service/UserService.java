package com.example.erpsystem.service;

import com.example.erpsystem.dto.JwtResponse;
import com.example.erpsystem.dto.user.SingIdDto;
import com.example.erpsystem.dto.user.UserRequestDto;
import com.example.erpsystem.dto.user.UserResponseDto;
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
        return modelMapper.map(entity, UserResponseDto.class);
    }

    @Override
    protected UserEntity mapCRToEntity(UserRequestDto createReq) {
        return modelMapper.map(createReq, UserEntity.class);
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
}
