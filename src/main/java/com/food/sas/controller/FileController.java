package com.food.sas.controller;

import com.food.sas.data.entity.FileInfo;
import com.food.sas.data.repository.FileInfoRepository;
import com.food.sas.data.response.Result;
import com.food.sas.exception.BadException;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.google.common.collect.Sets;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
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

    @ApiOperation("上传文件")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<String> uploadFile(@RequestPart("file") FilePart filePart) throws IOException {
        String extension = FilenameUtils.getExtension(filePart.filename());
        File newFile = File.createTempFile(UUID.randomUUID().toString(), "." + extension);
        filePart.transferTo(newFile);
        StorePath storePath = fastFileStorageClient.uploadFile(new FileInputStream(newFile), newFile.length(), extension, Sets.newHashSet());
        String fullPath = storePath.getFullPath();
        String name = FilenameUtils.getName(fullPath);
        String prefix = fullPath.replace(name, "");
        fileRepository.save(FileInfo.builder().name(filePart.filename()).path(name).prefix(prefix).build());
        newFile.deleteOnExit();
        return Result.success(name);
    }

    @ApiOperation("下载文件")
    @GetMapping
    public Mono<Void> download(@RequestParam("path") String path, ServerHttpResponse response) throws IOException {
        FileInfo fileInfo = fileRepository.findByPath(path);
        if (Objects.isNull(fileInfo)) {
            throw new BadException("文件不存在");
        }
        StorePath storePath = StorePath.parseFromUrl(fileInfo.getPrefix() + fileInfo.getPath());
        byte[] bytes = fastFileStorageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByteArray());
        String extension = FilenameUtils.getExtension(path);
        File temp = File.createTempFile(UUID.randomUUID().toString(), "." + extension);
        FileCopyUtils.copy(bytes, temp);
        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(fileInfo.getName(), "UTF-8"));
        response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return zeroCopyResponse.writeWith(temp, 0, temp.length());
    }
}
