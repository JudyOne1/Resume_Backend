package com.hc.resume_backend.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * 
 * @TableName detailinfo
 */
@TableName(value ="detailinfo")
@Data
public class Detailinfo implements Serializable {
    /**
     * id
     */
    @TableId
    private Long id;

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
     * 自我介绍
     */
    @ApiParam("自我介绍")
    private String introduce;

    /**
     * 创建时间
     */
    @ApiParam("创建时间")
    private Date createtime;

    /**
     * 更新时间
     */
    @ApiParam("更新时间")
    private Date updatetime;

    /**
     * 是否删除
     */
    private Integer isdelete;

    public Detailinfo(Long pid, String gender, String nationality, String police_face, String mail, String phone) {
        this.pid = pid;
        this.gender = gender;
        this.nationality = nationality;
        this.policeface = police_face;
        this.mail = mail;
        this.phone = phone;
    }
}