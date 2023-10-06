package com.example.erpsystem.controller;


import com.example.erpsystem.dto.base.JwtResponse;
import com.example.erpsystem.dto.user.*;
import com.example.erpsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public JwtResponse singUp(@RequestBody UserRequestDto userRequestDto) {
        return userService.singUp(userRequestDto);
    }

    @PostMapping("/sign-in")
    public JwtResponse singIn(@RequestBody SingIdDto singIdDto) {
        return userService.singIn(singIdDto);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') and hasAuthority('USER_GET')")
    @GetMapping("/get-all")
    public List<UserResponseDto> getAll(@RequestParam(defaultValue = "1") int size, @RequestParam(defaultValue = "1") int page) {
        return userService.getAll(page, size);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') and hasAuthority('CHANGE_ROLE')")
    @PostMapping("/change-role")
    public UserResponseDto changeRole(@RequestBody UpdateUserRoleDto dto) {
        return userService.updateUserRole(dto);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') and hasAuthority('CHANGE_PERMISSION')")
    @PostMapping("/change-permissions")
    public UserResponseDto changePermissions(@RequestBody UpdateUserPermissionsDto dto) {
        return userService.updateUserPermissions(dto);
    }
}
