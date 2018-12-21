package com.food.sas.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
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
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

/**
 * Created by zj on 2018/12/20
 */
@Api(tags = "File")
@RestController
@RequestMapping("/files")
public class FileController {

    @Value("${file-path}")
    private String filePath;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<String> uploadFile(@RequestPart("file") FilePart filePart) throws IOException {
        Path pathTemp = Paths.get(filePath);
        Path path = Files.createTempFile(pathTemp, "sas", filePart.filename()+ UUID.randomUUID());
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        DataBufferUtils.write(filePart.content(), channel, 0).subscribe();
        return Mono.just(path.toString());
    }

    @GetMapping
    public Mono<Void> download(@RequestParam("path") String path, ServerHttpResponse response) {
        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
        File file = Paths.get(path).toFile();
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return zeroCopyResponse.writeWith(file, 0, file.length());
    }
}
