package com.food.sas.util;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zj on 2019/1/6
 */
public interface TreeNode<ID extends Number> {

    ID getId();

    ID getParentId();

    <T extends TreeNode<ID>> void setChildren(List<T> children);

}