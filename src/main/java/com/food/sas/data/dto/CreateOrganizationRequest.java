package com.food.sas.data.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author zj on 2019/11/14.
 */
@Data
public class CreateOrganizationRequest {

    @NotEmpty(message = "组织名称不能为空")
    private String name;

    @NotEmpty(message = "证明材料不能为空")
    private String credential;

}
