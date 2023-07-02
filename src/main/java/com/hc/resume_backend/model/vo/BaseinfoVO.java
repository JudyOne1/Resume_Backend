package com.hc.resume_backend.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName baseinfo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseinfoVO implements Serializable {

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


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}