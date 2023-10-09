package com.example.erpsystem.entity;

import com.example.erpsystem.entity.enums.LessonStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "lessons")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonEntity extends BaseEntity{
    private UUID groupId;
    private Integer module;
    @Column(unique = true,nullable = false)
    private Integer lessonNumber;
    @Enumerated(EnumType.STRING)
    private LessonStatus lessonStatus;
    @OneToMany
    private Map<UserEntity,AttendanceEntity> attendance;
}
