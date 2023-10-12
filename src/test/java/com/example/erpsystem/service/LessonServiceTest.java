package com.example.erpsystem.service;

import com.example.erpsystem.entity.LessonEntity;
import com.example.erpsystem.entity.enums.LessonStatus;
import com.example.erpsystem.exception.WrongInputException;
import com.example.erpsystem.repository.GroupRepository;
import com.example.erpsystem.repository.LessonRepository;
import com.example.erpsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LessonServiceTest {
    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private UserRepository userRepository;
    private LessonService lessonService;
    private ModelMapper modelMapper;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelMapper=new ModelMapper();
        lessonService=new LessonService(lessonRepository,modelMapper,groupRepository,userRepository);
    }

    @Test
    void startLessonSucceed() {
        UUID lessonId=UUID.randomUUID();
        LessonEntity lessonEntity = new LessonEntity(null, 1, 1, LessonStatus.CREATED, null);
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lessonEntity));
        lessonService.startLesson(lessonId);
        assertEquals(LessonStatus.STARTED,lessonEntity.getLessonStatus());
        verify(lessonRepository).save(any(LessonEntity.class));
    }
    @Test
    void endLessonSucceed() {
        UUID lessonId=UUID.randomUUID();
        LessonEntity lessonEntity = new LessonEntity(null, 1, 1, LessonStatus.STARTED, null);
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lessonEntity));
        lessonService.endLesson(lessonId);
        assertEquals(LessonStatus.COMPLETED,lessonEntity.getLessonStatus());
        verify(lessonRepository).save(any(LessonEntity.class));
    }
    @Test
    void startLessonThrowsException(){
        UUID lessonId=UUID.randomUUID();
        LessonEntity lessonEntity = new LessonEntity(null, 1, 1, LessonStatus.STARTED, null);
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lessonEntity));
       assertThrows(WrongInputException.class,()->{
           lessonService.startLesson(lessonId);
       });
    }
    @Test
    void endLessonThrowsException(){
        UUID lessonId=UUID.randomUUID();
        LessonEntity lessonEntity = new LessonEntity(null, 1, 1, LessonStatus.COMPLETED, null);
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lessonEntity));
       assertThrows(WrongInputException.class,()->{
           lessonService.startLesson(lessonId);
       });
    }

}