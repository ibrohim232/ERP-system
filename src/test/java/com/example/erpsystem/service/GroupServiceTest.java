package com.example.erpsystem.service;

import com.example.erpsystem.dto.group.AddStudentInGroupDto;
import com.example.erpsystem.dto.user.UserRequestDto;
import com.example.erpsystem.entity.GroupEntity;
import com.example.erpsystem.entity.LessonEntity;
import com.example.erpsystem.entity.UserEntity;
import com.example.erpsystem.entity.enums.GroupStatus;
import com.example.erpsystem.entity.enums.LessonStatus;
import com.example.erpsystem.entity.enums.UserRole;
import com.example.erpsystem.repository.CourseRepository;
import com.example.erpsystem.repository.GroupRepository;
import com.example.erpsystem.repository.LessonRepository;
import com.example.erpsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GroupServiceTest {
    @Mock
    private  UserRepository userRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private  LessonRepository lessonRepository;
    @Mock
    private  CourseRepository courseRepository;
    @Mock
    private  UserService userService;
    @Mock
    private  CourseService courseService;
    @Mock
    private  PasswordEncoder passwordEncoder;
    @Mock
    private  Random random = new Random();

    private GroupService groupService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        groupService=new GroupService(groupRepository,modelMapper,userRepository,lessonRepository,
                courseRepository,userService,courseService,passwordEncoder);
    }

    @Test
    void addStudent(){
        UUID groupId = UUID.randomUUID();
        UserRequestDto userRequestDto = new UserRequestDto("Ali", "ali", "1111", "1111", "ali@gmail.com");
        AddStudentInGroupDto addStudentInGroupDto = new AddStudentInGroupDto(groupId, userRequestDto);
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupStatus(GroupStatus.STARTED);
        groupEntity.setStudents(new ArrayList<>());
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(groupEntity));
        when(userRepository.findByUserName(addStudentInGroupDto.getStudent().getUserName()))
                .thenReturn(Optional.empty());

        UserEntity userEntity = new UserEntity(addStudentInGroupDto.getStudent().getFullName(), addStudentInGroupDto.getStudent().getUserName(),
                addStudentInGroupDto.getStudent().getPassword(), addStudentInGroupDto.getStudent().getPhoneNumber(),
                UserRole.STUDENT, null, null, false, 0);
        when(modelMapper.map(addStudentInGroupDto.getStudent(), UserEntity.class))
                .thenReturn(userEntity);

        groupService.addStudent(addStudentInGroupDto);

        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void changeModuleTest(){
        UUID groupId=UUID.randomUUID();
        GroupEntity groupEntity = new GroupEntity();
        List<LessonEntity> lessonEntities=new ArrayList<>();
        for (int i = 0; i <12 ; i++) {
            lessonEntities.add(new LessonEntity(groupId,1,i+1,LessonStatus.COMPLETED,null));
        }
        groupEntity.setModule(1);
        groupEntity.setGroupStatus(GroupStatus.STARTED);

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(groupEntity));
        when(lessonRepository.findLessonEntitiesByGroupIdAndModule(groupEntity.getId(),groupEntity.getModule()))
                .thenReturn(Optional.of(lessonEntities));

        groupService.changeModule(groupId);

        verify(groupRepository).save(any(GroupEntity.class));
    }
    @Test
    void startGroupTest(){
        UUID uuid = UUID.randomUUID();
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupStatus(GroupStatus.PENDING);

        when(groupRepository.findById(uuid)).thenReturn(Optional.of(groupEntity));

        groupService.startGroup(uuid);
        verify(groupRepository).save(any(GroupEntity.class));
    }
    @Test
    void finishGroupTest() {
        UUID groupId = UUID.randomUUID();
        GroupEntity groupEntity = new GroupEntity();
        List<LessonEntity> lessonEntities=new ArrayList<>();
        for (int i = 0; i <12 ; i++) {
            lessonEntities.add(new LessonEntity(groupId,1,i+1,LessonStatus.CREATED,null));
        }
        groupEntity.setGroupStatus(GroupStatus.STARTED);
        groupEntity.setModule(1);

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(groupEntity));

        when(lessonRepository.findLessonEntitiesByGroupIdAndModule(groupEntity.getId(), groupEntity.getModule()))
                .thenReturn(Optional.of(lessonEntities));

        groupService.finishGroup(groupId);

        verify(groupRepository).save(any(GroupEntity.class));
        for (LessonEntity lessonEntity : lessonEntities) {
            assertEquals(LessonStatus.COMPLETED, lessonEntity.getLessonStatus());
        }
    }

}