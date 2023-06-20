package com.hc.resume_backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * 
 * @TableName uploadfileinfo
 */
@TableName(value ="uploadfileinfo")
@Data
public class Uploadfileinfo implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 对应简历id
     */
    private Long pid;

    /**
     * 系统自动生成UUID+后缀作为key
     */
    @ApiParam("系统自动生成UUID+后缀作为key")
    private String resumekey;

    /**
     * 华为云储存地址
     */
    @ApiParam("华为云储存地址")
    private String obsurl;

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
        Uploadfileinfo other = (Uploadfileinfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getResumekey() == null ? other.getResumekey() == null : this.getResumekey().equals(other.getResumekey()))
            && (this.getObsurl() == null ? other.getObsurl() == null : this.getObsurl().equals(other.getObsurl()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getResumekey() == null) ? 0 : getResumekey().hashCode());
        result = prime * result + ((getObsurl() == null) ? 0 : getObsurl().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pid=").append(pid);
        sb.append(", resumekey=").append(resumekey);
        sb.append(", obsurl=").append(obsurl);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}