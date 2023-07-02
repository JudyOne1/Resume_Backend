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
 * @TableName workinfo
 */
@TableName(value ="workinfo")
@Data
public class WorkinfoVO implements Serializable {

    /**
     * 对应简历个人id
     */
    @ApiParam("简历id")
    private Long pid;

    /**
     * 工作开始时间
     */
    @ApiParam("工作开始时间")
    private Date workbegin;

    /**
     * 工作结束时间
     */
    @ApiParam("工作结束时间")
    private Date workend;

    /**
     * 此工作时间
     */
    @ApiParam("此工作时间")
    private String worktime;

    /**
     * 公司名称
     */
    @ApiParam("公司名称")
    private String companyinfo;

    /**
     * 职位信息
     */
    @ApiParam("职位信息")
    @TableField("'position'")
    private String position;

    /**
     * 工作描述
     */
    @ApiParam("工作描述")
    private String workdesc;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}