package com.food.sas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by zj on 2018/12/21
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetectionResultModel {

    private boolean success;

    private String msg;
}
