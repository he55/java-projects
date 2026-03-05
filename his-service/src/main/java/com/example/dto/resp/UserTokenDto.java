package com.example.dto.resp;

import lombok.Data;

@Data
public class UserTokenDto {
    public String userId;
    public String userName;
    public String token;
}
