package com.food.sas.controller;

import com.food.sas.data.dto.CommentRequest;
import com.food.sas.data.dto.NewsDTO;
import com.food.sas.data.response.Result;
import com.food.sas.service.INewsService;
import com.food.sas.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * @author Created by ygdxd_admin at 2018-12-22 2:41 PM
 */
@Api(tags = "新闻管理")
@RequestMapping("/api/news")
@RestController
@AllArgsConstructor
public class NewsController {

    private INewsService newsService;

    private IUserService userService;

    @ApiOperation("获得单个新闻内容")
    @GetMapping("/{id}")
    public Result<NewsDTO> getNewsById(@PathVariable Long id) throws IOException {
        NewsDTO dto = newsService.getNewsById(id);
        if (dto.getStoreUrl() != null) {
            Path path = Paths.get(dto.getStoreUrl());
            if (Files.exists(path)) {
                byte[] data = Files.readAllBytes(path);
                dto.setContent(new String(data));
            }
        }
        return Result.success(dto);
    }

    @ApiOperation("获取新闻资讯列表")
    @GetMapping
    public Result<List<NewsDTO>> getNewsList(NewsDTO dto, @RequestParam(value = "current", defaultValue = "1") int page,
                                             @RequestParam(value = "size", defaultValue = "20") int size) {
        Page<NewsDTO> result = newsService.getNewsList(dto, PageRequest.of(Math.max(page - 1, 0), size));
        return Result.success(result.getContent(), result);
    }


    @ApiOperation("新增新闻")
    @PostMapping
    public Mono<Void> releaseNews(@RequestBody NewsDTO body) throws IOException {
        Path data = FileSystems.getDefault().getPath("/data", UUID.randomUUID().toString() + ".md");
        Files.createFile(data);
        Files.write(data, body.getContent().getBytes());
        body.setStoreUrl(data.toAbsolutePath().toString());
        newsService.saveNews(body);
        return Mono.empty();
    }

    @ApiOperation("修改新闻内容")
    @PutMapping("/{id}")
    public Mono<Void> updateNews(@RequestBody NewsDTO body, @PathVariable Long id) throws IOException {
        Path old = Paths.get(body.getStoreUrl());
        Files.write(old, body.getContent().getBytes());
        newsService.saveNews(body);
        return Mono.empty();
    }

    @ApiOperation("删除新闻")
    @DeleteMapping("/{id}")
    public Mono<Void> deleteNews(@PathVariable Long id) throws IOException {
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

    @PostMapping("/comments")
    public Mono<Void> createComment(@RequestBody @Valid CommentRequest request) {
        return newsService.createComment(request);
    }
}
