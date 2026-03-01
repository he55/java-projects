package com.example.service;

import com.example.exception.BusinessException;
import com.example.properties.StorageProperties;
import com.example.dto.DirectoryDto;
import com.example.dto.FileDto;
import com.example.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    private final StorageProperties storageProperties;

    public FileServiceImpl(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    private @NonNull Path getBasePath(String org) {
        String baseDir = storageProperties.getLocations().get(org);
        if (baseDir == null) {
            throw new BusinessException("机构编号无效 " + org);
        }
        return Path.of(baseDir);
    }

    @Override
    public List<DirectoryDto> getFolders(Integer id, String org) {
        Path basePath = getBasePath(org);
        Path path = basePath.resolve("files/" + id);
        if (!Files.exists(path)) {
            log.warn("没有找到目录 {}", path);
            return List.of();
        }

        try {
            return FileUtil.getFolders(path, basePath);
        } catch (IOException e) {
            throw new BusinessException("读取目录失败");
        }
    }

    @Override
    public List<FileDto> getFiles(String dir, String org) {
        if (!StringUtils.hasText(dir)) {
            throw new BusinessException("目录不能为空");
        }

        Path basePath = getBasePath(org);
        Path path = basePath.resolve(dir);
        if (!Files.exists(path)) {
            throw new BusinessException("目录不存在 " + path);
        }

        try {
            List<FileDto> files = FileUtil.getFiles(path, basePath);
            return files.stream().peek(fileDto -> {
                fileDto.setUrl("http://localhost:8080/files/download?filename=%s&org=%s"
                        .formatted(fileDto.getUrl(), org));
            }).toList();
        } catch (IOException e) {
            throw new BusinessException("读取文件失败");
        }
    }

    @Override
    public Resource loadAsResource(String filename, String org) {
        Path basePath = getBasePath(org);
        Path file = basePath.resolve(filename);
        if (!Files.exists(file)) {
            throw new BusinessException("文件不存在");
        }

        try {
            return new UrlResource(file.toUri());
        } catch (MalformedURLException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("文件读取失败");
        }
    }
}
