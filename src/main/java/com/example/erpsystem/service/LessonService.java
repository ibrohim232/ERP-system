package com.example.erpsystem.service;

import com.example.erpsystem.dto.lesson.AttendanceDto;
import com.example.erpsystem.dto.lesson.LessonRequestDto;
import com.example.erpsystem.dto.lesson.LessonResponseDto;
import com.example.erpsystem.entity.AttendanceEntity;
import com.example.erpsystem.entity.GroupEntity;
import com.example.erpsystem.entity.LessonEntity;
import com.example.erpsystem.entity.UserEntity;
import com.example.erpsystem.entity.enums.LessonStatus;
import com.example.erpsystem.exception.DataNotFoundException;
import com.example.erpsystem.exception.WrongInputException;
import com.example.erpsystem.repository.GroupRepository;
import com.example.erpsystem.repository.LessonRepository;
import com.example.erpsystem.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

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
        HashMap<String, Boolean> attendanceList = new HashMap<>();
        for (AttendanceEntity attendance : entity.getAttendance()) {
            attendanceList.put(attendance.getUser().getUsername(),attendance.getAttendance());
        }
        lessonResponseDto.setAttendance(attendanceList);
        return lessonResponseDto;
    }

    @Override
    protected LessonEntity mapCRToEntity(LessonRequestDto createReq) {
        LessonEntity lessonEntity = modelMapper.map(createReq, LessonEntity.class);
        ArrayList<AttendanceEntity> attendanceList = new ArrayList<>();
        lessonEntity.setAttendance(attendanceList);
        return lessonEntity;
    }

    public void endLesson(UUID id) {
        LessonEntity lessonEntity = repository.findById(id).orElseThrow(() -> new WrongInputException("lesson with id: " + id + " not found"));
        if (lessonEntity.getLessonStatus() == LessonStatus.COMPLETED || lessonEntity.getLessonStatus() == LessonStatus.CREATED) {
            throw new WrongInputException("lesson already finished or lesson has not started yet");
        }
        lessonEntity.setLessonStatus(LessonStatus.COMPLETED);
        repository.save(lessonEntity);
    }

    public void startLesson(UUID id) {
        LessonEntity lessonEntity = repository.findById(id).orElseThrow(() -> new WrongInputException("lesson with id: " + id + " not found"));
        if (lessonEntity.getLessonStatus() == LessonStatus.COMPLETED ||  lessonEntity.getLessonStatus() == LessonStatus.STARTED) {
            throw new WrongInputException("lesson already started or already finished");
        }
        lessonEntity.setLessonStatus(LessonStatus.STARTED);
        repository.save(lessonEntity);
    }


}
