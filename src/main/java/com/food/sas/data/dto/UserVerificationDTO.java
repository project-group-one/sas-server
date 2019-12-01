package com.food.sas.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Created by ygdxd_admin at 2019-11-25 10:32 PM
 */
@ApiModel("用户审核信息")
@Getter
@Setter
@ToString
public class UserVerificationDTO implements Serializable {

    private static final long serialVersionUID = 6001403612110253342L;

    private Integer id;

    @ApiModelProperty("要审核的用户的id")
    private Long userId;

    @ApiModelProperty("审核文件path")
    private String path;

    @ApiModelProperty("身份证正面")
    private String frontPath;

    @ApiModelProperty("身份证反面")
    private String backPath;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty("审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime auditTime;

    @ApiModelProperty("审核状态 0 未审核 1 已审核")
    private Integer status;
}
