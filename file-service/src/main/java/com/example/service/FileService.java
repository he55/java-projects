package com.example.service;

import com.example.dto.DirectoryDto;
import com.example.dto.FileDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    List<DirectoryDto> getFolders(Integer id, String org);

    List<FileDto> getFiles(String dir, String org);

    Resource getFileAsResource(String filename, String org);

    void uploadFile(MultipartFile file, String dir, String org) throws IOException;
}
