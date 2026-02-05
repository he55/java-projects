package com.example.util;

import com.example.dto.DirectoryDto;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileHelper {

    private FileHelper() {
    }

    public static List<DirectoryDto> getDirs(Path dir) {
        return getDirs(dir, dir);
    }

    private static List<DirectoryDto> getDirs(Path dir, Path baseDir) {
        List<Path> pathList;

        try {
            log.info("开始读取文件夹 {}", dir);
            pathList = Files.list(dir).filter(Files::isDirectory).toList();
        } catch (IOException e) {
            log.error("读取文件夹失败", e);
            throw new RuntimeException(e);
        }

        List<DirectoryDto> list = new ArrayList<>();

        for (Path p : pathList) {
            DirectoryDto directoryDto = new DirectoryDto();
            directoryDto.setName(p.getFileName().toString());
            directoryDto.setPath(baseDir.relativize(p).toString());
            directoryDto.setChildren(getDirs(p, baseDir));
            list.add(directoryDto);
        }

        if (list.isEmpty()) {
            return null;
        }

        return list;
    }
}
