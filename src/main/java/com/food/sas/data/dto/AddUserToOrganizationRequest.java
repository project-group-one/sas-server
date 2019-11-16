package com.food.sas.data.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zj on 2019/11/14.
 */
@Data
public class AddUserToOrganizationRequest {

    @NotNull(message = "userIds不能为空")
    private List<Long> userIds;

    @NotNull(message = "组织Id不能为空")
    private Long orgId;
}
