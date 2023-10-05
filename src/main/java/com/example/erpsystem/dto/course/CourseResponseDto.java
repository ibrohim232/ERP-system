package com.example.erpsystem.dto.course;

import com.example.erpsystem.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseResponseDto extends BaseDto {
    private String courseName;
    private Integer moduleCount;
}
