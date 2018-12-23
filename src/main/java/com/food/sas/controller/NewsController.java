package com.food.sas.controller;

import com.food.sas.data.dto.BaseResult;
import com.food.sas.data.dto.NewsDTO;
import com.food.sas.service.INewsService;
import com.food.sas.util.PathUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

/**
 * @author Created by ygdxd_admin at 2018-12-22 2:41 PM
 */
@Api(tags = "news")
@RequestMapping("/news")
@RestController
public class NewsController {

    @Autowired
    private INewsService newsService;


    @GetMapping("/{id}")
    public BaseResult<?> getNewsById(@PathVariable Integer id) throws IOException {
        NewsDTO dto = newsService.getNewsById(id);
        System.out.println(dto.getStoreUrl());
        if (dto.getStoreUrl() != null) {
            Path path = Paths.get(dto.getStoreUrl());
            if (Files.exists(path)) {
                byte[] data = Files.readAllBytes(path);
                dto.setContent(new String(data));
            }
        }
        return new BaseResult(dto);
    }

    @GetMapping
    public BaseResult<?> getNewsList(NewsDTO dto, @RequestParam(value = "page", defaultValue = "1") int page,
                                     @RequestParam(value = "size", defaultValue = "20") int size) {
        Page<NewsDTO> result = newsService.getNewsList(dto, PageRequest.of(page, size));
        return new BaseResult<List<NewsDTO>>(result.getContent(), result);
    }


    @PostMapping
    public Mono<?> releaseNews(@RequestBody NewsDTO body) throws IOException {
//        String html = org.apache.commons.text.StringEscapeUtils.escapeHtml4(body.getContent());
        Path data = FileSystems.getDefault().getPath("/data", UUID.randomUUID().toString() + ".md");
        Files.createFile(data);
        Files.write(data, body.getContent().getBytes());
        body.setStoreUrl(data.toAbsolutePath().toString());
        newsService.saveNews(body);

        return Mono.empty();
    }

    @PutMapping("/{id}")
    public Mono<?> updateNews(@RequestBody NewsDTO body, @PathVariable Integer id) throws IOException {
        Path old = Paths.get(body.getStoreUrl());
        Files.write(old, body.getContent().getBytes());
        return Mono.empty();
    }

    @DeleteMapping("/{id}")
    public Mono<?> deleteNews(@PathVariable Integer id) throws IOException {
        NewsDTO dto = newsService.getNewsById(id);
        if (dto.getStoreUrl() != null) {
            Path deleteFile = Paths.get(dto.getStoreUrl());
            if (Files.exists(deleteFile)) {
                Files.deleteIfExists(deleteFile);
            }
        }
        newsService.deleteNews(id);
        return Mono.empty();
    }
}
