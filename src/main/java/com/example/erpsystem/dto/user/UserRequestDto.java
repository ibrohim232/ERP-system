package com.example.erpsystem.dto.user;

import com.example.erpsystem.entity.enums.Permissions;
import com.example.erpsystem.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequestDto {
    private String fullName;
    private String userName;
    private String password;
    private String phoneNumber;
    private UserRole role;
    private List<Permissions> permissions;
}
