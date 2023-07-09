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
 * @TableName capacityinfo
 */
@TableName(value ="capacityinfo")
@Data
public class Capacityinfo implements Serializable {
    /**
     * id
     */
    @TableId
    private Long id;

    private Long pid;

    /**
     * 雷达图json
     */
    @ApiParam("雷达图json")
    private String skill;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 是否删除
     */
    private Integer isdelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}