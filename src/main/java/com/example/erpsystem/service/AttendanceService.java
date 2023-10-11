package com.example.erpsystem.service;

import com.example.erpsystem.dto.lesson.AttendanceDto;
import com.example.erpsystem.dto.lesson.LessonResponseDto;
import com.example.erpsystem.entity.AttendanceEntity;
import com.example.erpsystem.entity.GroupEntity;
import com.example.erpsystem.entity.LessonEntity;
import com.example.erpsystem.entity.UserEntity;
import com.example.erpsystem.entity.enums.GroupStatus;
import com.example.erpsystem.entity.enums.LessonStatus;
import com.example.erpsystem.exception.DataNotFoundException;
import com.example.erpsystem.repository.AttendanceRepository;
import com.example.erpsystem.repository.GroupRepository;
import com.example.erpsystem.repository.LessonRepository;
import com.example.erpsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final LessonRepository lessonRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AttendanceRepository attendanceRepository;

    public boolean attendanceTaking(AttendanceDto attendanceDto){
        GroupEntity groupEntity = groupRepository.findById(attendanceDto.getGroupId())
                .orElseThrow(() -> new DataNotFoundException("group not found"));
        List<LessonEntity> lessonEntities = lessonRepository.findLessonEntitiesByGroupId(attendanceDto.getGroupId())
                .orElseThrow(() -> new DataNotFoundException("lessons not found"));
        LessonEntity startedLesson = lessonEntities.stream()
                .filter(lesson -> lesson.getLessonStatus().equals(LessonStatus.STARTED))
                .findFirst()
                .orElseThrow(()->new DataNotFoundException("lesson not found by id in loop"));
        if (groupEntity.getGroupStatus().equals(GroupStatus.STARTED)){
            ArrayList<AttendanceEntity> attendanceEntities = new ArrayList<>();
            for (Map.Entry<UUID, Boolean> entry :attendanceDto.getAttendance().entrySet()) {
                UserEntity user = userRepository.findById(entry.getKey())
                        .orElseThrow(()->new DataNotFoundException("user not found by id in loop"));
                AttendanceEntity attendanceEntity = new AttendanceEntity(startedLesson, user, entry.getValue());
                attendanceEntities.add(attendanceEntity);
                attendanceRepository.save(attendanceEntity);
            }
            if (attendanceDto.getMentorId().equals(groupEntity.getMentor().getId())){
                startedLesson.setAttendance(attendanceEntities);
                lessonRepository.save(startedLesson);
            }
            return true;
        }
      return false;
    }

    public Map<String,Boolean> getByLesson(UUID lessonId){
        LessonEntity lessonEntity = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new DataNotFoundException("lesson found by id in loop"));
        LessonResponseDto lessonResponseDto = modelMapper.map(lessonEntity, LessonResponseDto.class);
        GroupEntity groupEntity = groupRepository.findById(lessonEntity.getGroupId())
                .orElseThrow(()->new DataNotFoundException("group not found."));
        lessonResponseDto.setGroupName(groupEntity.getGroupName());
        HashMap<String, Boolean> attendanceList = new HashMap<>();
        for (AttendanceEntity attendance : lessonEntity.getAttendance()) {
            attendanceList.put(attendance.getUser().getUsername(),attendance.getAttendance());
        }
        lessonResponseDto.setAttendance(attendanceList);
        return lessonResponseDto.getAttendance();
    }

    public Map<Integer,Map<String,Boolean>> getByStudent(UUID studentId){
        List<AttendanceEntity> attendanceEntities = attendanceRepository.findAttendanceEntitiesByUserId(studentId)
                .orElseThrow(() -> new RuntimeException("no info for this id"));
        HashMap<Integer, Map<String, Boolean>> result = new HashMap<>();
        for (AttendanceEntity attendanceEntity : attendanceEntities) {
            HashMap<String, Boolean> attendance = new HashMap<>();
            attendance.put(attendanceEntity.getUser().getUsername(),attendanceEntity.getAttendance());
            result.put(attendanceEntity.getLesson().getLessonNumber(),attendance);
        }
        return result;
    }

}
