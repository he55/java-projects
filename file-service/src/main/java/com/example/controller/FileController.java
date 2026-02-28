package com.example.controller;

import com.example.dto.DirectoryDto;
import com.example.dto.FileDto;
import com.example.exception.BusinessException;
import com.example.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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

    private static void validOrg(String org) {
        if (!StringUtils.hasText(org)) {
            throw new BusinessException("机构编号不能为空");
        }
    }

    @GetMapping("/getDirectories")
    public List<DirectoryDto> getDirectories(@RequestParam Integer id, @RequestParam String org) {
        validOrg(org);

        return fileService.getDirectories(id, org);
    }

    @GetMapping("/getFiles")
    public List<FileDto> getFiles(@RequestParam String dir, @RequestParam String org) {
        validOrg(org);

        return fileService.getFiles(dir, org);
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadFile(@RequestParam String filename, @RequestParam String org) {
        validOrg(org);

        Resource resource = fileService.loadAsResource(filename, org);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }
}
