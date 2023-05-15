package com.hc.resume_backend.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户更新请求
 *
 */
@Data
@ApiModel("用户更新请求体")
public class UserUpdateRequest implements Serializable {

    /**
     * id
     */
    @ApiModelProperty("用户id")
    private Long id;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String userName;

    /**
     * 用户角色：user/admin/ban
     */
    @ApiModelProperty("用户角色: user, admin")
    private String userRole;

    private static final long serialVersionUID = 1L;
}