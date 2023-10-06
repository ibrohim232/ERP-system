package com.example.erpsystem.dto.user;

import com.example.erpsystem.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserRoleDto {
    private UUID userId;
    private UserRole role;
}
