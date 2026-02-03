package com.example.service;

import com.example.dto.DirectoryDto;
import com.example.util.FileHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public List<DirectoryDto> getDirectories() {
        String dir = "/Users/hwz/Desktop/Cmd1";
        return FileHelper.getDirs(dir);
    }
}
