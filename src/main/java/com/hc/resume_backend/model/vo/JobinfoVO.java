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
 * @TableName jobinfo
 */
@Data
@AllArgsConstructor
public class JobinfoVO implements Serializable {

    /**
     * 职位id
     */
    @ApiParam("职位id(既jobid)")
    private Long jobid;

    /**
     * 职位名称
     */
    @ApiParam("职位名称")
    private String jobname;

    /**
     * 岗位职责
     */
    @ApiParam("岗位职责")
    private String responsibility;

    /**
     * 岗位要求
     */
    @ApiParam("岗位要求")
    private String requirement;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    public JobinfoVO() {

    }
}