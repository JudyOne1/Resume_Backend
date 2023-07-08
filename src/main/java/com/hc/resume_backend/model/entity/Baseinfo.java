package com.hc.resume_backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName baseinfo
 */
@TableName(value ="baseinfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Baseinfo implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 简历id
     */
    @ApiParam("简历id")
    private Long pid;

    /**
     * 姓名
     */
    @ApiParam("姓名")
    private String name;

    /**
     * 年龄
     */
    @ApiParam("年龄")
    private Integer age;

    /**
     * 最高学历
     */
    @ApiParam("最高学历")
    private String level;

    /**
     * 毕业院校
     */
    @ApiParam("毕业院校")
    private String collage;

    /**
     * 工作年限
     */
    @ApiParam("工作年限")
    private Double workyears;

    /**
     * 标签集合
     */
    @ApiParam("标签集合")
    private String alltag;

    /**
     * 创建时间
     */
    @ApiParam("创建时间")
    private Date createtime;

    /**
     * 更新时间
     */
    @ApiParam("更新时间")
    private Date updatetime;

    /**
     * 是否删除
     */
    @ApiParam("是否删除")
    private Integer isdelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Baseinfo(Long pid, String name, Integer age, String level, String collage, Double workyears, String alltag) {
        this.pid = pid;
        this.name = name;
        this.age = age;
        this.level = level;
        this.collage = collage;
        this.workyears = workyears;
        this.alltag = alltag;
    }

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
        Baseinfo other = (Baseinfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getAge() == null ? other.getAge() == null : this.getAge().equals(other.getAge()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
            && (this.getCollage() == null ? other.getCollage() == null : this.getCollage().equals(other.getCollage()))
            && (this.getWorkyears() == null ? other.getWorkyears() == null : this.getWorkyears().equals(other.getWorkyears()))
            && (this.getAlltag() == null ? other.getAlltag() == null : this.getAlltag().equals(other.getAlltag()))
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
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getAge() == null) ? 0 : getAge().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        result = prime * result + ((getCollage() == null) ? 0 : getCollage().hashCode());
        result = prime * result + ((getWorkyears() == null) ? 0 : getWorkyears().hashCode());
        result = prime * result + ((getAlltag() == null) ? 0 : getAlltag().hashCode());
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
        sb.append(", name=").append(name);
        sb.append(", age=").append(age);
        sb.append(", level=").append(level);
        sb.append(", collage=").append(collage);
        sb.append(", workyears=").append(workyears);
        sb.append(", alltag=").append(alltag);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}