package com.hc.resume_backend.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户创建请求
 *
 */
@Data
@ApiModel("用户创建请求体")
public class UserAddRequest implements Serializable {

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String userName;

    /**
     * 账号
     */
    @ApiModelProperty("账号")
    private String userAccount;


    /**
     * 用户角色: user, admin
     */
    @ApiModelProperty("用户角色: user, admin")
    private String userRole;

    private static final long serialVersionUID = 1L;
}