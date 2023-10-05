package com.example.erpsystem.entity;

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
    @OneToMany
    private List<UserEntity> students;
    @Column(columnDefinition = "int default 0")
    private int module;
}
