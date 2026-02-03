package com.example.service;

import com.example.dto.DirectoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public List<DirectoryDto> getDirectories() {
        DirectoryDto directoryDto = new DirectoryDto();
        directoryDto.setName("Images");
        directoryDto.setPath("/imgs");
        return List.of(directoryDto);
    }
}
