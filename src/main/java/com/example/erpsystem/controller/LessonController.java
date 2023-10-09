package com.example.erpsystem.controller;

import com.example.erpsystem.dto.lesson.AttendanceDto;
import com.example.erpsystem.service.AttendanceService;
import com.example.erpsystem.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {
    private final AttendanceService attendanceService;
    @PreAuthorize("hasAnyRole('ADMIN','MENTOR') and hasAuthority('LESSON_UPDATE')")
    @PostMapping("/check")
    public Boolean attendanceTaking(@RequestBody AttendanceDto attendanceDto){
        return attendanceService.attendanceTaking(attendanceDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MENTOR') and hasAuthority('LESSON_GET')")
    @GetMapping("/get-by-lesson")
    public Map<String,Boolean> getByLesson(@RequestParam UUID lessonId){
        return attendanceService.getByLesson(lessonId);
    }
}
