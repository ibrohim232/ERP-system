package com.example.erpsystem.entity;

import com.example.erpsystem.entity.enums.GroupStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupEntity extends BaseEntity {
    private String groupName;
    @OneToOne
    private UserEntity mentor;
    @ManyToOne
    private CourseEntity course;
    private GroupStatus groupStatus;
    @OneToMany
    private List<UserEntity> students;
    @Column(columnDefinition = "int default 1")
    private int module;
}
