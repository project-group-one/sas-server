package com.food.sas.service;

import com.food.sas.data.dto.NewsDTO;
import com.food.sas.data.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Created by ygdxd_admin at 2018-12-22 9:12 PM
 */
public interface INewsService {

    boolean saveNews(NewsDTO dto);

    NewsDTO getNewsById(Integer id);

    boolean deleteNews(Integer id);

    Page<NewsDTO> getNewsList(NewsDTO dto, Pageable pageable);
}
