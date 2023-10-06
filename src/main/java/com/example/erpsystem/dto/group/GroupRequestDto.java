package com.example.erpsystem.dto.group;

import com.example.erpsystem.dto.user.UserRequestDto;
import com.example.erpsystem.dto.user.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Getter
public class GroupRequestDto {
    private String groupName;
    private UUID mentor;
    private UUID course;
    private List<UserRequestDto> students;
}
