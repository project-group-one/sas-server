package com.food.sas.data.dto;

import com.food.sas.util.TreeNode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zj on 2019/1/6
 */
@Getter
@Setter
public class FoodTypeModel implements TreeNode<Long> {

    private Long id;

    private String name;

    private Long parentId;

    private List children = new ArrayList<>();

}
