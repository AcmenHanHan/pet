package com.logon.pet.petlogoncore.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Domain 公共类,子类需要重写变量以映射到数据库
 */
public abstract class AbstractDomain implements Serializable {

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 删除标志 @CommonDeleteFlag
     */
    private String deleteFlag;

    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 删除时间
     */
    private Date deleteTime;

    /**
     * 删除人
     */
    private String deleteBy;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getDeleteBy() {
        return deleteBy;
    }

    public void setDeleteBy(String deleteBy) {
        this.deleteBy = deleteBy;
    }

    /**
     * sava 前置方法
     */
    public void perSave() {
        if(this.version == null) {
            this.version = 0;
        }
        if(this.deleteFlag == null) {
            this.deleteFlag ="0";
        }
        if(this.createTime == null) {
            this.createTime = new Date();
        }
        if(this.createBy == null) {
            this.createBy = "";
        }
    }

    /**
     * update前置方法
     */
    public final void perUpdate() {
        this.updateTime = new Date();
    }
}
