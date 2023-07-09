package com.hc.resume_backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName baseinfo
 */
@TableName(value ="baseinfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Baseinfo implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 简历id
     */
    @ApiParam("简历id")
    private Long pid;

    /**
     * 姓名
     */
    @ApiParam("姓名")
    private String name;

    /**
     * 年龄
     */
    @ApiParam("年龄")
    private Integer age;

    /**
     * 最高学历
     */
    @ApiParam("最高学历")
    private String level;

    /**
     * 毕业院校
     */
    @ApiParam("毕业院校")
    private String collage;

    /**
     * 工作年限
     */
    @ApiParam("工作年限")
    private Double workyears;

    /**
     * 标签集合
     */
    @ApiParam("标签集合")
    private String alltag;

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
    @ApiParam("是否删除")
    private Integer isdelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Baseinfo(Long pid, String name, Integer age, String level, String collage, Double workyears, String alltag) {
        this.pid = pid;
        this.name = name;
        this.age = age;
        this.level = level;
        this.collage = collage;
        this.workyears = workyears;
        this.alltag = alltag;
    }



}