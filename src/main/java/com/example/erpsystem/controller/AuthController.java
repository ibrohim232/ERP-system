package com.example.erpsystem.controller;


import com.example.erpsystem.dto.JwtResponse;
import com.example.erpsystem.dto.user.SingIdDto;
import com.example.erpsystem.dto.user.UserRequestDto;
import com.example.erpsystem.dto.user.UserResponseDto;
import com.example.erpsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public UserResponseDto singUp(@RequestBody UserRequestDto userRequestDto) {
        return userService.create(userRequestDto);
    }

    @PostMapping("sign-in")
    public JwtResponse singIn(@RequestBody SingIdDto singIdDto) {
        return userService.singIn(singIdDto);
    }
    

}
