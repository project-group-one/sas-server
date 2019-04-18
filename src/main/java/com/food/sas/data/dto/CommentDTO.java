package com.food.sas.data.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Created by zj on 2019-04-18
 */
@Getter
@Setter
@EqualsAndHashCode
public class CommentDTO {

    private Long id;

    private String content;

    private Long userId;

    private String userName;

    private LocalDateTime createdDate;
}
