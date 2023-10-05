package com.example.erpsystem.dto.user;

import com.example.erpsystem.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDto extends BaseDto {
    private String fullName;
    private String userName;
    private String password;
    private String phoneNumber;
}
