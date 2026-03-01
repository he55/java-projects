package com.example.controller;

import com.example.dto.req.LoginDto;
import com.example.dto.resp.UserTokenDto;
import com.example.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public UserTokenDto login(@Valid @RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }
}
