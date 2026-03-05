package com.example.dto;

import lombok.Data;

import java.util.List;

@Data
public class DirectoryDto {
    private String name;
    private String path;
    private List<DirectoryDto> children;
}
