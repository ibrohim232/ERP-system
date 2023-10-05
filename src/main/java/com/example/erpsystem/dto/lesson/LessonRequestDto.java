package com.example.erpsystem.dto.lesson;

import com.example.erpsystem.entity.enums.LessonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LessonRequestDto {
    private UUID groupId;
    private Integer module;
    private Integer lessonNumber;
    private LessonStatus lessonStatus;
    private Map<UUID,Boolean> attendance;
}
