package com.example.erpsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseEntity extends BaseEntity{
    {
        this.isActive=true;
    }
    @Column(unique = true)
    private String courseName;
    private Integer moduleCount;
    private Boolean isActive;
}
