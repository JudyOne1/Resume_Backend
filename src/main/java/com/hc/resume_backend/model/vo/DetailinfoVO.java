package com.hc.resume_backend.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName detailinfo
 */
@Data
@AllArgsConstructor
public class DetailinfoVO implements Serializable {

    /**
     * 对应简历id
     */
    @ApiParam("简历id")
    private Long pid;

    /**
     * 性别
     */
    @ApiParam("性别")
    private String gender;

    /**
     * 居住地址
     */
    @ApiParam("居住地址")
    private String address;


    /**
     * 生日
     */
    @ApiParam("生日")
    private Date birthday;

    /**
     * 民族
     */
    @ApiParam("民族")
    @TableField("nationality")
    private String nationality;

    /**
     * 政治面貌
     */
    @ApiParam("政治面貌")
    @TableField("policeFace")
    private String policeface;
    /**
     * 邮箱
     */
    @ApiParam("邮箱")
    private String mail;

    /**
     * 手机号
     */
    @ApiParam("手机号")
    private String phone;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}