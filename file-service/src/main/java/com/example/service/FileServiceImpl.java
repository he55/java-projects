package com.example.service;

import com.example.StorageProperties;
import com.example.dto.DirectoryDto;
import com.example.dto.FileDto;
import com.example.util.FileHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    private final Path rootLocation;

    public FileServiceImpl(StorageProperties storageProperties) {
        rootLocation = Paths.get(storageProperties.getLocation());
    }

    @Override
    public List<DirectoryDto> getDirectories(Integer id) {
        Path dir = rootLocation.resolve("" + id);
        if (!Files.exists(dir)) {
            log.error("Directory not found {}", dir);
            throw new RuntimeException("Directory not found " + dir);
        }

        return FileHelper.getDirs(dir);
    }

    @Override
    public List<FileDto> getFiles(String dir) {
        Path path = rootLocation.resolve(dir);
        if (!Files.exists(path)) {
            log.error("Not found folder {}", path);
            throw new RuntimeException("Not found folder " + path);
        }

        try {
            List<FileDto> list = Files.list(path).filter(p -> {
                try {
                    return !Files.isDirectory(p) && !Files.isHidden(p);
                } catch (IOException e) {
                    log.error("读取文件异常", e);
                    return false;
                }
            }).map(p -> {
                String s = rootLocation.relativize(p).toString();
                String s1 = URLEncoder.encode(s, StandardCharsets.UTF_8);

                FileDto fileDto = new FileDto();
                fileDto.setFileName(p.getFileName().toString());
                fileDto.setUrl("http://localhost:8080/files/download?filename=" + s1);
                return fileDto;
            }).toList();
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        Path file = rootLocation.resolve(filename);
        try {
            UrlResource urlResource = new UrlResource(file.toUri());
            if (!urlResource.exists()) {
                log.error("Not found file {}", filename);
                throw new RuntimeException("Not found file " + filename);
            }
            return urlResource;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
