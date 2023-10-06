package com.example.erpsystem.dto.group;

import com.example.erpsystem.dto.user.UserRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddStudentInGroupDto {
    private UUID groupId;
    private UserRequestDto student;
}
