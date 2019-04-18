package com.food.sas.data.dto;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(hidden = true)
    private Long userId;

    @ApiModelProperty(hidden = true)
    private String userName;
}
