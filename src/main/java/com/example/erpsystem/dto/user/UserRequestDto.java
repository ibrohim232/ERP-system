package com.example.erpsystem.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequestDto {
    private String fullName;
    private String userName;
    private String password;
    private String phoneNumber;
}
