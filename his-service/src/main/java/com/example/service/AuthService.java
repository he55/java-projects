package com.example.service;

import com.example.dto.req.LoginDto;
import com.example.dto.resp.UserTokenDto;

public interface AuthService {
    UserTokenDto login(LoginDto loginDto);
}
