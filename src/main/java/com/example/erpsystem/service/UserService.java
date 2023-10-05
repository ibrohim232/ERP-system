package com.example.erpsystem.service;

import com.example.erpsystem.dto.user.UserRequestDto;
import com.example.erpsystem.dto.user.UserResponseDto;
import com.example.erpsystem.entity.UserEntity;
import com.example.erpsystem.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService extends BaseService<UserEntity, UUID, UserRepository, UserResponseDto, UserRequestDto> {
    public UserService(UserRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper);
    }

    @Override
    protected UserResponseDto mapEntityToRES(UserEntity entity) {
        return modelMapper.map(entity, UserResponseDto.class);
    }

    @Override
    protected UserEntity mapCRToEntity(UserRequestDto createReq) {
        return modelMapper.map(createReq, UserEntity.class);
    }
}
