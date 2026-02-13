package com.example.controller;

import com.example.dto.DirectoryDto;
import com.example.dto.FileDto;
import com.example.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/getDirectories")
    public List<DirectoryDto> getDirectories(@RequestParam Integer id) {
        return fileService.getDirectories(id);
    }

    @GetMapping("/getFiles")
    public List<FileDto> getFiles(@RequestParam String dir) {
        return fileService.getFiles(dir);
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadFile(@RequestParam String filename) {
        Resource resource = fileService.loadAsResource(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }
}
