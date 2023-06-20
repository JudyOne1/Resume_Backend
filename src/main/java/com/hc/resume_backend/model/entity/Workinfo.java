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
 * @TableName workinfo
 */
@TableName(value ="workinfo")
@Data
public class Workinfo implements Serializable {
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
        Workinfo other = (Workinfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getWorkbegin() == null ? other.getWorkbegin() == null : this.getWorkbegin().equals(other.getWorkbegin()))
            && (this.getWorkend() == null ? other.getWorkend() == null : this.getWorkend().equals(other.getWorkend()))
            && (this.getWorktime() == null ? other.getWorktime() == null : this.getWorktime().equals(other.getWorktime()))
            && (this.getCompanyinfo() == null ? other.getCompanyinfo() == null : this.getCompanyinfo().equals(other.getCompanyinfo()))
            && (this.getPosition() == null ? other.getPosition() == null : this.getPosition().equals(other.getPosition()))
            && (this.getWorkdesc() == null ? other.getWorkdesc() == null : this.getWorkdesc().equals(other.getWorkdesc()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getIsdelete() == null ? other.getIsdelete() == null : this.getIsdelete().equals(other.getIsdelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getWorkbegin() == null) ? 0 : getWorkbegin().hashCode());
        result = prime * result + ((getWorkend() == null) ? 0 : getWorkend().hashCode());
        result = prime * result + ((getWorktime() == null) ? 0 : getWorktime().hashCode());
        result = prime * result + ((getCompanyinfo() == null) ? 0 : getCompanyinfo().hashCode());
        result = prime * result + ((getPosition() == null) ? 0 : getPosition().hashCode());
        result = prime * result + ((getWorkdesc() == null) ? 0 : getWorkdesc().hashCode());
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
        sb.append(", workbegin=").append(workbegin);
        sb.append(", workend=").append(workend);
        sb.append(", worktime=").append(worktime);
        sb.append(", companyinfo=").append(companyinfo);
        sb.append(", position=").append(position);
        sb.append(", workdesc=").append(workdesc);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}