package com.example.controller;

import com.example.dto.UserDto;
import com.example.dto.req.LoginDto;
import com.example.dto.resp.UserTokenDto;
import com.example.exception.BusinessException;
import com.example.pojo.User;
import com.example.service.UserService;
import com.example.util.JwtUtil;
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
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public UserTokenDto login(@Valid @RequestBody LoginDto loginDto) {
        User user = userService.findByIdAndPassword(loginDto.getUserId(), loginDto.getPassword(), loginDto.getOrg());
        if (user == null) {
            throw new BusinessException("用户名或者密码错误");
        }

        String token = jwtUtil.generateToken(new UserDto(loginDto.getOrg(), String.valueOf(loginDto.getUserId())));

        UserTokenDto userTokenDto = new UserTokenDto();
        userTokenDto.setUserId(user.getUserId());
        userTokenDto.setUserName(user.getUserName());
        userTokenDto.setToken(token);
        return userTokenDto;
    }
}
