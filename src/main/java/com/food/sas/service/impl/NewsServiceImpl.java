package com.food.sas.service.impl;

import com.food.sas.data.dto.NewsDTO;
import com.food.sas.data.entity.News;
import com.food.sas.data.entity.QNews;
import com.food.sas.data.repository.NewsRepository;
import com.food.sas.mapper.NewsMapper;
import com.food.sas.service.INewsService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public NewsDTO getNewsById(Integer id) {
        return NewsMapper.MAPPER.fromEntity(newsRepository.findById(id).get());
    }

    @Override
    public boolean deleteNews(Integer id) {
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

}
