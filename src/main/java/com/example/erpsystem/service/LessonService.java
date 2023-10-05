package com.example.erpsystem.service;

import com.example.erpsystem.dto.lesson.LessonRequestDto;
import com.example.erpsystem.dto.lesson.LessonResponseDto;
import com.example.erpsystem.entity.AttendanceEntity;
import com.example.erpsystem.entity.GroupEntity;
import com.example.erpsystem.entity.LessonEntity;
import com.example.erpsystem.entity.UserEntity;
import com.example.erpsystem.exception.DataNotFoundException;
import com.example.erpsystem.repository.GroupRepository;
import com.example.erpsystem.repository.LessonRepository;
import com.example.erpsystem.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
@Service
public class LessonService extends BaseService<
        LessonEntity, UUID, LessonRepository,
        LessonResponseDto, LessonRequestDto
        >{
    public LessonService(LessonRepository repository, ModelMapper modelMapper, GroupRepository groupRepository, UserRepository userRepository) {
        super(repository, modelMapper);
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Override
    protected LessonResponseDto mapEntityToRES(LessonEntity entity) {
        LessonResponseDto lessonResponseDto = modelMapper.map(entity, LessonResponseDto.class);
        GroupEntity groupEntity = groupRepository.findById(entity.getGroupId())
                .orElseThrow(()->new DataNotFoundException("group not found."));
        lessonResponseDto.setGroupName(groupEntity.getGroupName());
        return lessonResponseDto;
    }

    @Override
    protected LessonEntity mapCRToEntity(LessonRequestDto createReq) {
        LessonEntity lessonEntity = modelMapper.map(createReq, LessonEntity.class);
        HashMap<UserEntity, AttendanceEntity> attendanceList = new HashMap<>();
        for (Map.Entry<UUID, Boolean> entry : createReq.getAttendance().entrySet()) {
            UserEntity user = userRepository.findById(entry.getKey())
                    .orElseThrow(()->new DataNotFoundException("user not found by id in loop"));
            AttendanceEntity attendanceEntity = new AttendanceEntity(entry.getValue());
            attendanceList.put(user,attendanceEntity);

        }
        lessonEntity.setAttendance(attendanceList);
        return lessonEntity;
    }
}
