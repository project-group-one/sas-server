package com.food.sas.controller;

import com.food.sas.data.dto.BaseResult;
import com.food.sas.data.entity.FileInfo;
import com.food.sas.data.repository.FileInfoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;

/**
 * Created by zj on 2018/12/20
 */
@Slf4j
@Api(tags = "File")
@RestController
@RequestMapping("/api/files")
@AllArgsConstructor
public class FileController {

    private FileInfoRepository fileRepository;

    @ApiOperation("上传文件")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResult uploadFile(@RequestPart("file") FilePart filePart) throws IOException {
        String extension = FilenameUtils.getExtension(filePart.filename());
        Path path = Files.createTempFile(String.valueOf(new Random().nextInt(1000)), "." + extension);
        log.info(path.toString());
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        DataBufferUtils.write(filePart.content(), channel, 0).subscribe();
        String url = path.getFileName().toString();
        String prefix = path.toString().replace(url, "");
        fileRepository.save(FileInfo.builder().name(filePart.filename()).path(url).prefix(prefix).build());
        return new BaseResult<>(url);
    }

    @ApiOperation("下载文件")
    @GetMapping
    public Mono<Void> download(@RequestParam("path") String path, ServerHttpResponse response) throws UnsupportedEncodingException {
        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
        FileInfo fileInfo = fileRepository.findByPath(path);
        File file = Paths.get(fileInfo.getPrefix() + path).toFile();
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(fileInfo.getName(), "UTF-8"));
        response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return zeroCopyResponse.writeWith(file, 0, file.length());
    }
}
