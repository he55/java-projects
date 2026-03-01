package com.example.service;

import com.example.exception.BusinessException;
import com.example.properties.StorageProperties;
import com.example.dto.DirectoryDto;
import com.example.dto.FileDto;
import com.example.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
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
        if (!StringUtils.hasText(org)) {
            throw new BusinessException("机构编号不能为空");
        }

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
    public Resource getFileAsResource(String filename, String org) {
        Path basePath = getBasePath(org);
        Path file = basePath.resolve(filename);
        if (!Files.exists(file)) {
            return null;
        }

        return FileUtil.getFileAsResource(file);
    }
}
