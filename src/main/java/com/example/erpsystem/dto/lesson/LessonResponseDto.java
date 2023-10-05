package com.example.erpsystem.dto.lesson;

import com.example.erpsystem.dto.BaseDto;
import com.example.erpsystem.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LessonResponseDto extends BaseDto {
    private String groupName;
    private Integer module;
    private Integer lessonNumber;
    private Status lessonStatus;
    private Map<String,Boolean> attendance;
}
