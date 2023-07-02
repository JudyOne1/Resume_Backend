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
 * @TableName taginfo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaginfoVO implements Serializable {
    /**
     * id
     */
    @TableId
    @ApiParam("标签id")
    private Long id;

    /**
     * 标签名称
     */
    @ApiParam("标签名称")
    private String tag;

    /**
     * 标签描述
     */
    @ApiParam("标签描述")
    @TableField("'desc'")
    private String desc;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}