package com.example.service;

import com.example.dto.DirectoryDto;
import com.example.dto.FileDto;
import org.springframework.core.io.Resource;

import java.util.List;

public interface FileService {

    List<DirectoryDto> getFolders(Integer id, String org);

    List<FileDto> getFiles(String dir, String org);

    Resource loadAsResource(String filename, String org);
}
