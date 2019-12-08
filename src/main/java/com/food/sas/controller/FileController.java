package com.food.sas.controller;

import com.food.sas.data.entity.FileInfo;
import com.food.sas.data.repository.FileInfoRepository;
import com.food.sas.data.response.Result;
import com.food.sas.exception.BadException;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by zj on 2018/12/20
 */
@Slf4j
@Api(tags = "File")
@RestController
@RequestMapping("/api/files")
@AllArgsConstructor
public class FileController {

    private final FileInfoRepository fileRepository;
    private final FastFileStorageClient fastFileStorageClient;
    private static final String GROUP = "normal-file";

    @ApiOperation("上传文件")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<String> uploadFile(@RequestPart("file") FilePart filePart) throws IOException {
        String extension = FilenameUtils.getExtension(filePart.filename());
        File classPath = new File(ResourceUtils.getURL("classpath:static").getPath());
        Path path = Paths.get(classPath.getAbsolutePath() + "/upload/");
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        Path newPath = Files.createFile(Paths.get(path + "/" + UUID.randomUUID().toString() + "." + extension));
        filePart.transferTo(newPath);
        File file = newPath.toFile();
        StorePath storePath = fastFileStorageClient.uploadFile(GROUP, new FileInputStream(file), file.length(), FilenameUtils.getExtension(extension));
        String url = storePath.getPath();
        fileRepository.save(FileInfo.builder().name(filePart.filename()).path(url).prefix(storePath.getGroup()).build());
        return Result.success(url);
    }

    @ApiOperation("下载文件")
    @GetMapping
    public Mono<Void> download(@RequestParam("path") String path, ServerHttpResponse response) throws IOException {
        FileInfo fileInfo = fileRepository.findByPath(path);
        if (Objects.isNull(fileInfo)) {
            throw new BadException("文件不存在");
        }
        String extension = FilenameUtils.getExtension(fileInfo.getName());
        Path newPath = FileSystems.getDefault().getPath("/file", UUID.randomUUID().toString() + extension);
        byte[] bytes = fastFileStorageClient.downloadFile(fileInfo.getPrefix(), fileInfo.getPath(), new DownloadByteArray());
        Files.write(newPath, bytes);
        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(fileInfo.getName(), "UTF-8"));
        response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return zeroCopyResponse.writeWith(newPath, 0, newPath.toFile().length());
    }
}
