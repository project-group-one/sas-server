package com.food.sas.controller;

import org.apache.commons.io.FilenameUtils;
import org.aspectj.util.FileUtil;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zj on 2019-04-20
 */
@RestController
public class FilePreViewController {

    @GetMapping("/uploads/{path}")
    public ByteArrayResource filePreView(@PathVariable("path") String path, ServerHttpResponse response) throws IOException {
        String extension = FilenameUtils.getExtension(path);
        List<String> images = Arrays.asList("jpg", "png", "jpeg");
        if (!images.contains(extension)) return new ByteArrayResource(new byte[]{});
        File classPath = new File(ResourceUtils.getURL("classpath:static").getPath());
        Path filePath = Paths.get(classPath.getAbsolutePath() + "/upload/" + path);
        response.getHeaders().setContentType(MediaType.IMAGE_PNG);
        byte[] bytes = FileUtil.readAsByteArray(filePath.toFile());
        return new ByteArrayResource(bytes);
    }
}
