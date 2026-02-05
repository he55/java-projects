package com.example.service;

import com.example.dto.DirectoryDto;
import com.example.dto.FileDto;
import org.springframework.core.io.Resource;

import java.util.List;

public interface FileService {
    List<DirectoryDto> getDirectories(Integer id);
    List<FileDto> getFiles(String dir);
    Resource loadAsResource(String filename);
}
