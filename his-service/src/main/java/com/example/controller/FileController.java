package com.example.controller;

import com.example.dto.DirectoryDto;
import com.example.dto.FileDto;
import com.example.exception.BusinessException;
import com.example.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/get-folders")
    public List<DirectoryDto> getFolders(@RequestParam Integer id, @RequestParam String org) {
        return fileService.getFolders(id, org);
    }

    @GetMapping("/get-files")
    public List<FileDto> getFiles(@RequestParam String dir, @RequestParam String org) {
        return fileService.getFiles(dir, org);
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadFile(@RequestParam String filename, @RequestParam String org) {
        Resource resource = fileService.getFileAsResource(filename, org);
        if (resource == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFile(@RequestParam MultipartFile file, @RequestParam String dir, @RequestParam String org) {
        try {
            fileService.uploadFile(file, dir, org);
        } catch (IOException e) {
            throw new BusinessException("文件上传失败");
        }
    }
}
