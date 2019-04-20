package com.food.sas.data.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by zj on 2019-04-18
 */
@Data
public class CommentRequest {

    @NotNull
    private Long newsId;

    @NotNull
    private String content;

    private Long userId;

    private String userName;
}
