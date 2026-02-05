package com.example;

import com.example.dto.DirectoryDto;
import com.example.dto.FileDto;
import com.example.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FileServiceApplicationTests {

    @Autowired
    private FileService fileService;

    @Test
    void getDirectories() {
        List<DirectoryDto> directories = fileService.getDirectories(645);
        System.out.println("directories = " + directories);
    }

    @Test
    void getFiles() {
        List<FileDto> files = fileService.getFiles("645/照片");
        System.out.println("files = " + files);
    }

}
