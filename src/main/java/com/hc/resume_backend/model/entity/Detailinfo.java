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
 * @TableName detailinfo
 */
@TableName(value ="detailinfo")
@Data
public class Detailinfo implements Serializable {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 对应简历id
     */
    @ApiParam("简历id")
    private Long pid;

    /**
     * 性别
     */
    @ApiParam("性别")
    private String gender;

    /**
     * 身高
     */
    @ApiParam("身高")
    private Double height;

    /**
     * 体重
     */
    @ApiParam("体重")
    private Double weight;

    /**
     * 生日
     */
    @ApiParam("生日")
    private Date birthday;

    /**
     * 居住地
     */
    @ApiParam("居住地")
    private String resident;

    /**
     * 籍贯
     */
    @ApiParam("籍贯")
    private String birthplane;

    /**
     * 邮箱
     */
    @ApiParam("邮箱")
    private String mail;

    /**
     * 手机号
     */
    @ApiParam("手机号")
    private String phone;

    /**
     * 期望职位
     */
    @ApiParam("期望职位")
    private String expectedposition;

    /**
     * 技能/证书
     */
    @ApiParam("技能/证书")
    private String skill;

    /**
     * 兴趣爱好
     */
    @ApiParam("兴趣爱好")
    private String hobby;

    /**
     * 自我介绍
     */
    @ApiParam("自我介绍")
    private String introduce;

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
        Detailinfo other = (Detailinfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getGender() == null ? other.getGender() == null : this.getGender().equals(other.getGender()))
            && (this.getHeight() == null ? other.getHeight() == null : this.getHeight().equals(other.getHeight()))
            && (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
            && (this.getBirthday() == null ? other.getBirthday() == null : this.getBirthday().equals(other.getBirthday()))
            && (this.getResident() == null ? other.getResident() == null : this.getResident().equals(other.getResident()))
            && (this.getBirthplane() == null ? other.getBirthplane() == null : this.getBirthplane().equals(other.getBirthplane()))
            && (this.getMail() == null ? other.getMail() == null : this.getMail().equals(other.getMail()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getExpectedposition() == null ? other.getExpectedposition() == null : this.getExpectedposition().equals(other.getExpectedposition()))
            && (this.getHobby() == null ? other.getHobby() == null : this.getHobby().equals(other.getHobby()))
            && (this.getIntroduce() == null ? other.getIntroduce() == null : this.getIntroduce().equals(other.getIntroduce()))
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
        result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
        result = prime * result + ((getHeight() == null) ? 0 : getHeight().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getBirthday() == null) ? 0 : getBirthday().hashCode());
        result = prime * result + ((getResident() == null) ? 0 : getResident().hashCode());
        result = prime * result + ((getBirthplane() == null) ? 0 : getBirthplane().hashCode());
        result = prime * result + ((getMail() == null) ? 0 : getMail().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getExpectedposition() == null) ? 0 : getExpectedposition().hashCode());
        result = prime * result + ((getHobby() == null) ? 0 : getHobby().hashCode());
        result = prime * result + ((getIntroduce() == null) ? 0 : getIntroduce().hashCode());
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
        sb.append(", gender=").append(gender);
        sb.append(", height=").append(height);
        sb.append(", weight=").append(weight);
        sb.append(", birthday=").append(birthday);
        sb.append(", resident=").append(resident);
        sb.append(", birthplane=").append(birthplane);
        sb.append(", mail=").append(mail);
        sb.append(", phone=").append(phone);
        sb.append(", expectedposition=").append(expectedposition);
        sb.append(", hobby=").append(hobby);
        sb.append(", introduce=").append(introduce);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}