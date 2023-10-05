package com.example.erpsystem.dto.lesson;

import com.example.erpsystem.entity.UserEntity;
import com.example.erpsystem.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LessonRequestDto {
    private UUID groupId;
    private Integer module;
    private Integer lessonNumber;
    private Status lessonStatus;
    private Map<String,Boolean> attendance;
}
