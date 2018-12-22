package com.food.sas.controller;

import com.food.sas.data.dto.BaseResult;
import com.food.sas.data.entity.FileInfo;
import com.food.sas.data.repository.FileInfoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(tags = "File")
@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileInfoRepository fileRepository;

    @Value("${file-path}")
    private String filePath;

    @ApiOperation("上传文件")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResult uploadFile(@RequestPart("file") FilePart filePart) throws IOException {
        Path pathTemp = Paths.get(filePath);
        String extension = FilenameUtils.getExtension(filePart.filename());
        Path path = Files.createTempFile(pathTemp, String.valueOf(new Random().nextInt(1000)), "." + extension);
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        DataBufferUtils.write(filePart.content(), channel, 0).subscribe();
        String url = path.getFileName().toString();
        fileRepository.save(FileInfo.builder().name(filePart.filename()).path(url).build());
        return new BaseResult<>(url);
    }

    @ApiOperation("下载文件")
    @GetMapping
    public Mono<Void> download(@RequestParam("path") String path, ServerHttpResponse response) throws UnsupportedEncodingException {
        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
        File file = Paths.get(filePath + path).toFile();
        FileInfo fileInfo = fileRepository.findByPath(file.getName());
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(fileInfo.getName(), "UTF-8"));
        response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return zeroCopyResponse.writeWith(file, 0, file.length());
    }
}
