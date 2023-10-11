package com.example.erpsystem.dto.course;

import com.example.erpsystem.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseResponseDto extends BaseDto {
    private String courseName;
    private Integer moduleCount;
}
