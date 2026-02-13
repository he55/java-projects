package com.example.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("storage")
public class StorageProperties {
    private String location = "upload-dir";
}
