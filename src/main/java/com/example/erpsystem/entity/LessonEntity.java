package com.example.erpsystem.entity;

import com.example.erpsystem.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "lesson")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonEntity extends BaseEntity{
    private UUID groupId;
    private Integer module;
    @Column(unique = true,nullable = false)
    private Integer lessonNumber;
    private Status lessonStatus;
    @OneToMany
    private List<UserEntity> attendance;
}
