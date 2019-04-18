package com.food.sas.service;

import com.food.sas.data.dto.CommentRequest;
import com.food.sas.data.dto.NewsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

/**
 * @author Created by ygdxd_admin at 2018-12-22 9:12 PM
 */
public interface INewsService {

    boolean saveNews(NewsDTO dto);

    NewsDTO getNewsById(Long id);

    boolean deleteNews(Long id);

    Page<NewsDTO> getNewsList(NewsDTO dto, Pageable pageable);

    Mono<Void> createComment(CommentRequest request);

}
