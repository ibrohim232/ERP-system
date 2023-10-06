package com.example.erpsystem.controller;

import com.example.erpsystem.dto.course.CourseRequestDto;
import com.example.erpsystem.dto.course.CourseResponseDto;
import com.example.erpsystem.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('COURSE_CREATE')")
    @PostMapping("/save")
    public CourseResponseDto save(@RequestBody CourseRequestDto courseRequestDto){
        return courseService.create(courseRequestDto);
    }

    @PreAuthorize("hasRole('ADMIN') and hasAuthority('COURSE_GET')")
    @GetMapping("/get")
    public CourseResponseDto get(@RequestParam UUID courseId){
        return courseService.findById(courseId);
    }

    @PreAuthorize("hasRole('ADMIN') and hasAuthority('COURSE_UPDATE')")
    @PostMapping("/update")
    public CourseResponseDto update(@RequestParam UUID courseId,@RequestBody CourseRequestDto courseRequestDto){
        return courseService.update(courseId,courseRequestDto);
    }

    @PreAuthorize("hasRole('ADMIN') and hasAuthority('COURSE_DELETE')")
    @GetMapping("/delete")
    public void delete(@RequestParam UUID courseId){
        courseService.deleteById(courseId);
    }

    @PreAuthorize("hasRole('ADMIN') and hasAuthority('COURSE_GET')")
    @GetMapping("/get-all")
    public List<CourseResponseDto> getAll(@RequestParam int pageNumber,@RequestParam int size){
        return courseService.getAll(pageNumber,size);
    }
}
