package com.example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginDto {

    @NotBlank(message = "机构不能为空")
    private String org;

    @Min(value = 1, message = "用户Id要大于0")
    private int userId;

    @NotBlank(message = "密码不能为空")
    @Length(min = 2, message = "密码不能小于2个字符")
    private String password;
}
