package com.hc.resume_backend.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class CapacityVO implements Serializable {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 证书或技能
     */
    @ApiParam("证书或技能")
    private String skill;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}