package com.hc.resume_backend.model.vo;

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
 * @TableName eduinfo
 */
@TableName(value ="eduinfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduinfoVO implements Serializable {


    /**
     * 对应简历个人id
     */
    @ApiParam("简历id")
    private Long pid;

    /**
     * 开始时间
     */
    @ApiParam("开始时间")
    private Date edubegin;

    /**
     * 结束时间
     */
    @ApiParam("结束时间")
    private Date eduend;

    /**
     * 院校名字
     */
    @ApiParam("院校名字")
    private String collage;

    /**
     * 专业名称
     */
    @ApiParam("专业名称")
    private String major;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}