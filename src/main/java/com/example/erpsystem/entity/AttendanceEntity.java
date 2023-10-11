package com.example.erpsystem.entity;

import com.example.erpsystem.service.LessonService;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "attendances")
public class AttendanceEntity extends BaseEntity{
    @ManyToOne(fetch = FetchType.EAGER)
    private LessonEntity lesson;
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity user;
    private Boolean attendance;
}
