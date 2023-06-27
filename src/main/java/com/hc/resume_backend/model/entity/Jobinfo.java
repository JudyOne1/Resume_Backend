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
 * @TableName jobinfo
 */
@TableName(value ="jobinfo")
@Data
public class Jobinfo implements Serializable {
    /**
     * id
     */
    @TableId
    private Long id;

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

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Jobinfo other = (Jobinfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getJobid() == null ? other.getJobid() == null : this.getJobid().equals(other.getJobid()))
            && (this.getJobname() == null ? other.getJobname() == null : this.getJobname().equals(other.getJobname()))
            && (this.getResponsibility() == null ? other.getResponsibility() == null : this.getResponsibility().equals(other.getResponsibility()))
            && (this.getRequirement() == null ? other.getRequirement() == null : this.getRequirement().equals(other.getRequirement()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getIsdelete() == null ? other.getIsdelete() == null : this.getIsdelete().equals(other.getIsdelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getJobid() == null) ? 0 : getJobid().hashCode());
        result = prime * result + ((getJobname() == null) ? 0 : getJobname().hashCode());
        result = prime * result + ((getResponsibility() == null) ? 0 : getResponsibility().hashCode());
        result = prime * result + ((getRequirement() == null) ? 0 : getRequirement().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getIsdelete() == null) ? 0 : getIsdelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", jobid=").append(jobid);
        sb.append(", jobname=").append(jobname);
        sb.append(", responsibility=").append(responsibility);
        sb.append(", requirement=").append(requirement);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}