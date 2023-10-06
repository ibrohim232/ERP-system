package com.example.erpsystem.dto.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttendanceDto {
    private UUID mentorId;
    private UUID groupId;
    private Map<UUID,Boolean> attendance;
}
