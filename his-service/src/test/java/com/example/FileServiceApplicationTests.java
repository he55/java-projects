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

//    @Test
    void getFolders() {
        List<DirectoryDto> folders = fileService.getFolders(645, "TEST");
        System.out.println("folders = " + folders);
    }

//    @Test
    void getFiles() {
        List<FileDto> files = fileService.getFiles("files/645/照片", "TEST");
        System.out.println("files = " + files);
    }

}
