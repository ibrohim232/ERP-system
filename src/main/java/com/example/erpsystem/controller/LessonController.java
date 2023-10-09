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
    private final LessonService lessonService;
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
    @PreAuthorize("hasAnyRole('ADMIN','MENTOR','STUDENT') and hasAuthority('LESSON_GET')")
    @GetMapping("/get-by-student")
    public Map<Integer, Map<String, Boolean>> getBy(@RequestParam UUID studentId){
        return attendanceService.getByStudent(studentId);
    }

    @PreAuthorize("hasAnyRole('MENTOR') and hasAuthority('LESSON_UPDATE')")
    @GetMapping("/end-lesson")
    public void endLesson(@RequestParam UUID id) {
        lessonService.endLesson(id);
    }

    @PreAuthorize("hasAnyRole('MENTOR') and hasAuthority('LESSON_UPDATE')")
    @GetMapping("/start-lesson")
    public void startLesson(@RequestParam UUID id) {
        lessonService.startLesson(id);
    }


}
