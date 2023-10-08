package com.example.erpsystem.dto.group;

import com.example.erpsystem.dto.base.BaseDto;
import com.example.erpsystem.dto.course.CourseResponseDto;
import com.example.erpsystem.dto.user.UserResponseDto;
import com.example.erpsystem.entity.CourseEntity;
import com.example.erpsystem.entity.UserEntity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@NoArgsConstructor
@Getter
public class GroupResponseDto extends BaseDto {
    private UserResponseDto mentor;
    private CourseResponseDto course;
    private List<UserResponseDto> students;
    private int module;

    public GroupResponseDto(UUID id, LocalDateTime created, LocalDateTime updated, UserResponseDto mentor, CourseResponseDto course, List<UserResponseDto> students, int module) {
        super(id, created, updated);
        this.mentor = mentor;
        this.course = course;
        this.students = students;
        this.module = module;
    }
}
