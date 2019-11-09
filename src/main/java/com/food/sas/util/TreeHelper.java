package com.food.sas.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zj on 2019/1/6
 */
public class TreeHelper {

    /**
     * @param parentID null 为根节点
     * @param nodes    数据
     * @return list
     */
    public static <ID extends Number, T extends TreeNode<ID>> List<T> buildTree(ID parentID, List<T> nodes) {
        // 根节点列表
        List<T> list = new ArrayList<>();
        // 顺序遍历节点列表，如果之前是有序的，那么构建树后同层级之间有序
        for (int i = 0; i < nodes.size(); i++) {
            T node = nodes.get(i);
            //递归入口， String.valueOf防止null值
            if (String.valueOf(node.getParentId()).equals(String.valueOf(parentID))) {
                // parentID作为入口
                List<T> children = buildTree(node.getId(), nodes);
                node.setChildren(children);
                list.add(node);
            }
        }
        return list;
    }
}