package com.example.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String org;
    private String userId;
    private String password;
}
