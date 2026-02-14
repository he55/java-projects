package com.example.controller;

import com.example.dto.UserDto;
import com.example.dto.LoginDto;
import com.example.service.CurrentUser;
import com.example.service.UserService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CurrentUser currentUser;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Object login(@RequestBody LoginDto loginDto) {
        if (!StringUtils.hasText(loginDto.getOrg())) {
            throw new RuntimeException("org required");
        }

        if (!StringUtils.hasText(loginDto.getPassword())) {
            throw new RuntimeException("password required");
        }

        String userName = userService.findByIdAndPassword(loginDto.getUserId(), loginDto.getPassword());
        if (userName == null) {
            throw new RuntimeException("username or password error");
        }

        String token = jwtUtil.generateToken(new UserDto(loginDto.getOrg(), String.valueOf(loginDto.getUserId())));

        Map<String, Object> map = new HashMap<>();
        map.put("userId", loginDto.getUserId());
        map.put("userName", userName);
        map.put("token", token);
        return map;
    }

    @PostMapping("/logout")
    public Object logout() {
        UserDto user = currentUser.getCurrentUser();

        Map<String, Object> map = new HashMap<>();
        map.put("msg", "success");
        map.put("userId", user.getUserId());
        map.put("org", user.getOrg());
        return map;
    }
}
