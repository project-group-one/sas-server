package com.food.sas.util;

import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

/**
 * Created by zj on 2018/12/22
 */
public class PathUtils {

    public static String getPath() {
        try {
            return ResourceUtils.getURL("resources/static/upload/").getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
