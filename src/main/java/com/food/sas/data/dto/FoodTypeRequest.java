package com.food.sas.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by zj on 2019/1/5
 */
@Getter
@Setter
public class FoodTypeRequest {

    @JsonProperty("title")
    private String name;

    private Long parentId;

    @JsonProperty("key")
    private Long id;
}
