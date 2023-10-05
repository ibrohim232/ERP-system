package com.example.erpsystem.service;

import com.example.erpsystem.dto.lesson.LessonRequestDto;
import com.example.erpsystem.dto.lesson.LessonResponseDto;
import com.example.erpsystem.entity.GroupEntity;
import com.example.erpsystem.entity.LessonEntity;
import com.example.erpsystem.exception.DataNotFoundException;
import com.example.erpsystem.repository.GroupRepository;
import com.example.erpsystem.repository.LessonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class LessonService extends BaseService<
        LessonEntity, UUID, LessonRepository,
        LessonResponseDto, LessonRequestDto
        >{
    public LessonService(LessonRepository repository, ModelMapper modelMapper, GroupRepository groupRepository) {
        super(repository, modelMapper);
        this.groupRepository = groupRepository;
    }
    private final GroupRepository groupRepository;

    @Override
    protected LessonResponseDto mapEntityToRES(LessonEntity entity) {
        LessonResponseDto lessonResponseDto = modelMapper.map(entity, LessonResponseDto.class);
        GroupEntity groupEntity = groupRepository.findById(entity.getGroupId())
                .orElseThrow(()->new DataNotFoundException("group not found."));
        lessonResponseDto.setGroupName(groupEntity.getGroupName());
        //entity.getAttendance().get()
        return null;
    }

    @Override
    protected LessonEntity mapCRToEntity(LessonRequestDto createReq) {
        return null;
    }
}
