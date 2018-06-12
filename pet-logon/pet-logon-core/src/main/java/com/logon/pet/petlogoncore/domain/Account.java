package com.logon.pet.petlogoncore.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户账号 Domain
 */
@Entity(name = "account")
@Table(name = "ACCOUNT")
public class Account extends AbstractDomain{

    /**
     * 账号序列SI,主键
     */
    @Id
    @Column(name = "ACCOUNT_SI")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    //@SequenceGenerator(name = "g_seq_account", sequenceName = "seq_account",allocationSize = 1)
    private Long accountSi;

    /**
     * 账号唯一ID
     */
    @Column(name = "ACCOUNT_ID")
    private String accountId;

    /**
     * 账号名称
     */
    @Column(name = "ACCOUNT_NAME")
    private String accountName;


    /**
     * 账号密码
     */
    @Column(name = "ACCOUNT_PASSWORD")
    private String accountPassword;

    /**
     * 账号状态
     */
    @Column(name = "ACCOUNT_STATUS")
    private String accountStatus;

    /**
     * 版本号
     */
    @Column(name = "VERSION")
    private Integer version;

    /**
     * 删除标志 @CommonDeleteFlag
     */
    @Column(name = "DELETE_FLAG")
    private String deleteFlag;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name = "CREATE_BY")
    private String createBy;

    /**
     * 修改时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 修改人
     */
    @Column(name = "UPDATE_BY")
    private String updateBy;

    /**
     * 删除时间
     */
    @Column(name = "DELETE_TIME")
    private Date deleteTime;

    /**
     * 删除人
     */
    @Column(name = "DELETE_BY")
    private String deleteBy;

    public Long getAccountSi() {
        return accountSi;
    }

    public void setAccountSi(Long accountSi) {
        this.accountSi = accountSi;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountSi=" + accountSi +
                ", accountId='" + accountId + '\'' +
                ", accountName='" + accountName + '\'' +
                ", accountPassword='" + accountPassword + '\'' +
                ", accountStatus='" + accountStatus + '\'' +
                ", version=" + version +
                ", deleteFlag='" + deleteFlag + '\'' +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", updateTime=" + updateTime +
                ", updateBy='" + updateBy + '\'' +
                ", deleteTime=" + deleteTime +
                ", deleteBy='" + deleteBy + '\'' +
                '}';
    }
}
