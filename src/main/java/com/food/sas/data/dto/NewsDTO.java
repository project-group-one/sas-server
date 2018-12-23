package com.food.sas.data.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Created by ygdxd_admin at 2018-12-22 3:44 PM
 */
@Data
public class NewsDTO implements Serializable {

    private static final long serialVersionUID = -3125369105515122379L;

    private String title;

    private Integer type;

    private String author;

    private String summary;

    private String source;

    private String imgUrl;

    private String keywords;

    private String content;

    private String storeUrl;

}
