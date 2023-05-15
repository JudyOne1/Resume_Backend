package com.hc.resume_backend.model.dto.user;

import com.hc.resume_backend.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户查询请求
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("用户查询请求体")
public class UserQueryRequest extends PageRequest implements Serializable {
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