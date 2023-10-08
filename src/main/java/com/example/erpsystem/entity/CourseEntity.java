package com.example.erpsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "courses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseEntity extends BaseEntity{
    @Column(unique = true)
    private String courseName;
    private Integer moduleCount;

}
