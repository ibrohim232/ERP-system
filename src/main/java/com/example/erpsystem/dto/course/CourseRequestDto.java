package com.example.erpsystem.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseRequestDto {
    private String courseName;
    private Integer moduleCount;
}
