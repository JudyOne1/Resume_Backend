package com.hc.resume_backend.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求
 *
 */
@Data
@ApiModel("用户登录请求体")
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    @ApiModelProperty("用户账号")
    private String userAccount;

    @ApiModelProperty("用户密码")
    private String userPassword;
}
