package com.food.sas.mapper;

import com.food.sas.data.dto.CommentRequest;
import com.food.sas.data.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by zj on 2018/12/21
 */
@Mapper
public interface CommentMapper {

    CommentMapper MAPPER = Mappers.getMapper(CommentMapper.class);

    Comment toEntity(CommentRequest request);

}
