package com.example.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("jwt-security")
public class JwtSecurityProperties {
    private String secret;
    private Long ttl;
    private String[] ignoreUrls = new String[0];
}
