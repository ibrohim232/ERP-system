package com.example.erpsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "course")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Course extends BaseEntity{
    {
        this.isActive=true;
    }
    @Column(unique = true)
    private String courseName;
    private Integer moduleCount;
    private Boolean isActive;
}
