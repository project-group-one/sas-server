package com.food.sas.controller;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zj on 2019-04-20
 */
@RestController
@AllArgsConstructor
public class FilePreViewController {

    private final FastFileStorageClient fastFileStorageClient;

    @GetMapping("/uploads/{path}")
    public ByteArrayResource filePreView(@PathVariable("path") String path, ServerHttpResponse response) {
        String extension = FilenameUtils.getExtension(path);
        List<String> images = Arrays.asList("jpg", "png", "jpeg");
        if (!images.contains(extension)) return new ByteArrayResource(new byte[]{});
        StorePath storePath = StorePath.parseFromUrl(path);
        byte[] bytes = fastFileStorageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByteArray());
        response.getHeaders().setContentType(MediaType.IMAGE_PNG);
        return new ByteArrayResource(bytes);
    }
}
