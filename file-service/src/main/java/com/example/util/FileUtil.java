package com.example.util;

import com.example.dto.DirectoryDto;
import com.example.dto.FileDto;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class FileUtil {

    private FileUtil() {
    }

    public static List<DirectoryDto> getDirs(Path path, Path basePath) throws IOException {
        try (Stream<Path> stream = Files.list(path)) {
            return stream.filter(Files::isDirectory)
                    .map(p -> {
                        DirectoryDto directoryDto = new DirectoryDto();
                        directoryDto.setName(p.getFileName().toString());
                        directoryDto.setPath(basePath.relativize(p).toString());

                        try {
                            List<DirectoryDto> dirs = getDirs(p, basePath);
                            if (!dirs.isEmpty()) {
                                directoryDto.setChildren(dirs);
                            }
                        } catch (IOException e) {
                            log.error("读取目录失败 {}", p);
                        }

                        return directoryDto;
                    }).toList();
        }
    }

    public static List<FileDto> getFiles(Path path, Path basePath) throws IOException {
        try (Stream<Path> stream = Files.list(path)) {
            return stream.filter(p -> !Files.isDirectory(p))
                    .map(p -> {
                        String s = basePath.relativize(p).toString();
                        String s1 = URLEncoder.encode(s, StandardCharsets.UTF_8);

                        FileDto fileDto = new FileDto();
                        fileDto.setFileName(p.getFileName().toString());
                        fileDto.setUrl(s1);
                        return fileDto;
                    }).toList();
        }
    }
}
