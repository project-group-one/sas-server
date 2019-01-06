package com.food.sas.data.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by zj on 2019/1/5
 */
@Getter
@Setter
public class FoodTypeRequest {

    private String name;

    private Long parentId;
}
