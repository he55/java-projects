package com.example.config;

import com.example.util.JwtUtil;
import com.example.properties.JwtSecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    JwtUtil jwtUtil(JwtSecurityProperties jwtSecurityProperties) {
        return new JwtUtil(jwtSecurityProperties.getSecret(), jwtSecurityProperties.getTtl());
    }
}
