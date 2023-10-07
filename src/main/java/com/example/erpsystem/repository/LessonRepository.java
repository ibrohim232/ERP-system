package com.example.erpsystem.repository;

import com.example.erpsystem.entity.GroupEntity;
import com.example.erpsystem.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<LessonEntity, UUID> {
    Optional<List<LessonEntity>> findLessonEntitiesByGroupId(UUID id);
}
