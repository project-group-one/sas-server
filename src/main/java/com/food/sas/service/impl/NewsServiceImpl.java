package com.food.sas.service.impl;

import com.food.sas.data.dto.CommentRequest;
import com.food.sas.data.dto.NewsDTO;
import com.food.sas.data.entity.Comment;
import com.food.sas.data.entity.News;
import com.food.sas.data.entity.QNews;
import com.food.sas.data.repository.NewsRepository;
import com.food.sas.mapper.CommentMapper;
import com.food.sas.mapper.NewsMapper;
import com.food.sas.service.INewsService;
import com.google.common.collect.Sets;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author Created by ygdxd_admin at 2018-12-22 9:13 PM
 */
@Service
public class NewsServiceImpl implements INewsService {

    @Autowired
    private NewsRepository newsRepository;


    @Override
    public boolean saveNews(NewsDTO dto) {
        News news = NewsMapper.MAPPER.toEntity(dto);
        if (StringUtils.isEmpty(news.getStoreUrl())) {
            return false;
        }
        return newsRepository.save(news) != null;
    }

    @Override
    public NewsDTO getNewsById(Long id) {
        return NewsMapper.MAPPER.fromEntity(newsRepository.findById(id).get());
    }

    @Override
    public boolean deleteNews(Long id) {
        newsRepository.deleteById(id);
        return true;
    }

    @Override
    public Page<NewsDTO> getNewsList(NewsDTO dto, Pageable pageable) {
        QNews qNews = QNews.news;
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.isNoneEmpty(dto.getTitle())) {
            builder.and(qNews.title.eq(dto.getTitle()));
        }
        if (StringUtils.isNoneEmpty(dto.getKeywords())) {
            builder.and(qNews.title.contains(dto.getKeywords()).or(qNews.keywords.contains(dto.getKeywords())));
        }
        if (StringUtils.isNoneEmpty(dto.getAuthor())) {
            builder.and(qNews.author.eq(dto.getAuthor()));
        }

        Page<News> page = newsRepository.findAll(builder, pageable);
        return new PageImpl<>(NewsMapper.MAPPER.fromEntitys(page.getContent()), pageable, page.getTotalElements());
    }

    @Override
    public Mono<Void> createComment(CommentRequest request) {
        Optional<News> newsOptional = newsRepository.findById(request.getNewsId());
        newsOptional.ifPresent(news -> {
            Comment comment = CommentMapper.MAPPER.toEntity(request);
            news.setComments(Sets.newHashSet(comment));
            newsRepository.save(news);
        });
        return Mono.empty();
    }
}
