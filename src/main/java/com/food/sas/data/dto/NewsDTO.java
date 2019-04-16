package com.food.sas.data.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Created by ygdxd_admin at 2018-12-22 3:44 PM
 */
@ApiModel("新闻")
@Data
public class NewsDTO implements Serializable {

    private static final long serialVersionUID = -3125369105515122379L;

    @ApiModelProperty("新闻标题")
    private String title;

    @ApiModelProperty("新闻类型 默认为0")
    private Integer type;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("简介 查询列表时显示在标题下方")
    private String summary;

    @ApiModelProperty("来源  （xx网）")
    private String source;

    @ApiModelProperty("新闻图片路径")
    private String imgUrl;

    @ApiModelProperty("关键字")
    private String keywords;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("内容存储地址")
    private String storeUrl;

    @ApiModelProperty("创建时间")
    private LocalDateTime releaseTime;

}
