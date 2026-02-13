package com.example.controller;

import com.example.dto.UserDto;
import com.example.dto.LoginDto;
import com.example.service.CurrentUser;
import com.example.component.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/login")
    public Object login(@RequestBody LoginDto loginDto) {
        if (!"root".equals(loginDto.getUserId()) || !"123".equals(loginDto.getPassword())) {
            throw new RuntimeException("账号或密码错误");
        }

        String token = jwtUtil.generateToken(new UserDto(loginDto.getOrg(), loginDto.getUserId()));

        Map<String, Object> map = new HashMap<>();
        map.put("userId", loginDto.getUserId());
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
