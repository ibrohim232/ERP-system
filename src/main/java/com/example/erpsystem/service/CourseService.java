package com.example.erpsystem.service;

import com.example.erpsystem.dto.course.CourseRequestDto;
import com.example.erpsystem.dto.course.CourseResponseDto;
import com.example.erpsystem.entity.CourseEntity;
import com.example.erpsystem.repository.CourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class CourseService extends BaseService<
        CourseEntity, UUID, CourseRepository,
         CourseResponseDto,CourseRequestDto
        >{
    public CourseService(CourseRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper);
    }

    @Override
    protected CourseResponseDto mapEntityToRES(CourseEntity entity) {
        return modelMapper.map(entity,CourseResponseDto.class);
    }

    @Override
    protected CourseEntity mapCRToEntity(CourseRequestDto createReq) {
        return modelMapper.map(createReq,CourseEntity.class);
    }

}
