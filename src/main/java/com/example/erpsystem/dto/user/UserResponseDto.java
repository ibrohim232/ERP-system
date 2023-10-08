package com.example.erpsystem.dto.user;

import com.example.erpsystem.dto.base.BaseDto;
import com.example.erpsystem.entity.enums.Permissions;
import com.example.erpsystem.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDto extends BaseDto {
    private String fullName;
    private String userName;
    private String phoneNumber;
    private UserRole role;
    private List<Permissions> permissions;

    public UserResponseDto(UUID id, LocalDateTime created, LocalDateTime updated, String fullName, String userName, String phoneNumber, UserRole role, List<Permissions> permissions) {
        super(id, created, updated);
        this.fullName = fullName;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.permissions = permissions;
    }
}
