package com.example.erpsystem.service;

import com.example.erpsystem.dto.group.AddStudentInGroupDto;
import com.example.erpsystem.dto.group.GroupRequestDto;
import com.example.erpsystem.dto.group.GroupResponseDto;
import com.example.erpsystem.dto.user.UserRequestDto;
import com.example.erpsystem.entity.CourseEntity;
import com.example.erpsystem.entity.GroupEntity;
import com.example.erpsystem.entity.UserEntity;
import com.example.erpsystem.entity.enums.GroupStatus;
import com.example.erpsystem.entity.enums.UserRole;
import com.example.erpsystem.exception.DataAlreadyExistsException;
import com.example.erpsystem.exception.DataNotFoundException;
import com.example.erpsystem.repository.CourseRepository;
import com.example.erpsystem.repository.GroupRepository;
import com.example.erpsystem.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class GroupService extends BaseService<GroupEntity, UUID, GroupRepository, GroupResponseDto, GroupRequestDto> {
    public GroupService(GroupRepository repository, ModelMapper modelMapper, UserRepository userRepository, CourseRepository courseRepository) {
        super(repository, modelMapper);
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Override
    protected GroupResponseDto mapEntityToRES(GroupEntity entity) {
        return modelMapper.map(entity, GroupResponseDto.class);
    }

    @Override
    protected GroupEntity mapCRToEntity(GroupRequestDto createReq) {
        UserEntity mentor = userRepository.
                findById(createReq.getMentor())
                .orElseThrow(() -> new DataNotFoundException("resource with id: " + createReq.getMentor() + " not found"));
        CourseEntity course = courseRepository.
                findById(createReq.getCourse())
                .orElseThrow(() -> new DataNotFoundException("resource with id: " + createReq.getCourse() + " not found"));
        if (mentor.getRole() != UserRole.MENTOR) {
            throw new DataNotFoundException("Mentor with id: " + createReq.getMentor() + " not found");
        }
        List<String> studentNames = createReq.getStudents().stream().map(UserRequestDto::getUserName).toList();
        Set<UserEntity> students = userRepository.findByUserNameIn(studentNames);
        createReq.getStudents().forEach(userRequestDto -> {
            students.add(new UserEntity(userRequestDto.getFullName(),
                    userRequestDto.getUserName(),
                    userRequestDto.getPassword(),
                    userRequestDto.getPhoneNumber(),
                    UserRole.USER,
                    null));
        });


        if (students.size() > 25) {
            throw new DataAlreadyExistsException("so many students");
        }
        students.forEach(userEntity -> userEntity.setRole(UserRole.STUDENT));
        userRepository.saveAll(students);

        return new GroupEntity(createReq.getGroupName(), mentor, course, GroupStatus.PENDING, students.stream().toList(), 1);
    }

    public void addStudent(AddStudentInGroupDto dto) {
        UserEntity student = userRepository.
                findByUserName(dto.getStudent().getUserName())
                .orElseThrow(() -> new DataNotFoundException("user not found"));
        GroupEntity group = repository.findById(dto.getGroupId())
                .orElseThrow(() -> new DataNotFoundException("resource with id: " + dto.getGroupId() + " not found"));
        if (group.getGroupStatus().equals(GroupStatus.FINISHED)) {
            throw new RuntimeException("group already finished ");
        }
        if (group.getStudents().size() < 25) {
            student.setRole(UserRole.STUDENT);
            userRepository.save(student);
            group.getStudents().add(student);
            repository.save(group);
        } else {
            throw new RuntimeException("so many students");
        }
    }

    public void startGroup(UUID groupId) {
        GroupEntity group = repository.findById(groupId)
                .orElseThrow(() -> new DataNotFoundException("resource with id: " + groupId + " not found"));
        if (group.getGroupStatus().equals(GroupStatus.FINISHED) || group.getGroupStatus().equals(GroupStatus.STARTED)) {
            throw new RuntimeException("group finished or already started");
        }
        group.setGroupStatus(GroupStatus.STARTED);
        repository.save(group);
    }

    public void finishGroup(UUID groupId) {
        GroupEntity group = repository.findById(groupId)
                .orElseThrow(() -> new DataNotFoundException("resource with id: " + groupId + " not found"));
        if (group.getGroupStatus().equals(GroupStatus.FINISHED)) {
            throw new RuntimeException("group already finished ");
        }
        group.setGroupStatus(GroupStatus.FINISHED);
        repository.save(group);
    }

    public void changeModule(UUID groupId) {
        GroupEntity group = repository.findById(groupId)
                .orElseThrow(() -> new DataNotFoundException("resource with id: " + groupId + " not found"));
        if (group.getGroupStatus().equals(GroupStatus.FINISHED)) {
            throw new RuntimeException("group already finished ");
        }
        group.setModule(group.getModule() + 1);
        if (group.getModule() == 9) {
            group.setGroupStatus(GroupStatus.FINISHED);
        }
        repository.save(group);
    }
}
