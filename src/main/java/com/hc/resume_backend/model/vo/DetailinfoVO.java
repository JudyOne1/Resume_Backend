package com.hc.resume_backend.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName detailinfo
 */
@Data
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
     * 身高
     */
    @ApiParam("身高")
    private Double height;

    /**
     * 体重
     */
    @ApiParam("体重")
    private Double weight;

    /**
     * 生日
     */
    @ApiParam("生日")
    private Date birthday;

    /**
     * 居住地
     */
    @ApiParam("居住地")
    private String resident;

    /**
     * 籍贯
     */
    @ApiParam("籍贯")
    private String birthplane;

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

    /**
     * 期望职位
     */
    @ApiParam("期望职位")
    private String expectedposition;

    /**
     * 技能/证书
     */
    @ApiParam("技能/证书")
    private String skill;

    /**
     * 兴趣爱好
     */
    @ApiParam("兴趣爱好")
    private String hobby;


    /**
     * 是否删除
     */
    private Integer isdelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}