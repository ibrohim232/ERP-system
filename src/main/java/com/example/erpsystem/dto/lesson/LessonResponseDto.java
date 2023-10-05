package com.example.erpsystem.dto.lesson;

import com.example.erpsystem.dto.BaseDto;
import com.example.erpsystem.entity.enums.LessonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LessonResponseDto extends BaseDto {
    private String groupName;
    private Integer module;
    private Integer lessonNumber;
    private LessonStatus lessonStatus;
    private Map<String,Boolean> attendance;
}
