package com.example.erpsystem.repository;

import com.example.erpsystem.entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, UUID> {
    Optional<List<AttendanceEntity>> findAttendanceEntitiesByUserId(UUID id);
}
