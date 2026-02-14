package com.example.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String org;
    private int userId;
    private String password;
}
