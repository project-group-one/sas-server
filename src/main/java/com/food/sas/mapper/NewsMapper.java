package com.food.sas.mapper;

import com.food.sas.data.dto.NewsDTO;
import com.food.sas.data.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Created by ygdxd_admin at 2018-12-22 11:02 PM
 */
@Mapper
public interface NewsMapper {

    NewsMapper MAPPER = Mappers.getMapper(NewsMapper.class);

    News toEntity(NewsDTO dto);

    NewsDTO fromEntity(News entity);

    List<NewsDTO> fromEntitys(List<News> entitys);


}
