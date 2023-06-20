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
 * @TableName eduinfo
 */
@TableName(value ="eduinfo")
@Data
public class Eduinfo implements Serializable {
    /**
     * id
     */
    @TableId
    private Long id;

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
        Eduinfo other = (Eduinfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getEdubegin() == null ? other.getEdubegin() == null : this.getEdubegin().equals(other.getEdubegin()))
            && (this.getEduend() == null ? other.getEduend() == null : this.getEduend().equals(other.getEduend()))
            && (this.getCollage() == null ? other.getCollage() == null : this.getCollage().equals(other.getCollage()))
            && (this.getMajor() == null ? other.getMajor() == null : this.getMajor().equals(other.getMajor()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getIsdelete() == null ? other.getIsdelete() == null : this.getIsdelete().equals(other.getIsdelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getEdubegin() == null) ? 0 : getEdubegin().hashCode());
        result = prime * result + ((getEduend() == null) ? 0 : getEduend().hashCode());
        result = prime * result + ((getCollage() == null) ? 0 : getCollage().hashCode());
        result = prime * result + ((getMajor() == null) ? 0 : getMajor().hashCode());
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
        sb.append(", pid=").append(pid);
        sb.append(", edubegin=").append(edubegin);
        sb.append(", eduend=").append(eduend);
        sb.append(", collage=").append(collage);
        sb.append(", major=").append(major);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}