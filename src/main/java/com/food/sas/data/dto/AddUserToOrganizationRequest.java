package com.food.sas.data.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zj on 2019/11/14.
 */
@Data
public class AddUserToOrganizationRequest {

    @NotNull(message = "userId不能为空")
    private Long userId;

    @NotNull(message = "组织Id不能为空")
    private Long orgId;
}
