package com.logon.pet.petlogoncore.domain;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "account")
public class User{

    private Long accountSi;
    private String accountId;
    private String accountName;


    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="g_seq_account")
    @SequenceGenerator(name = "g_seq_account", sequenceName = "seq_account",allocationSize = 1)
    @Column(name = "account_si")
    public Long getAccountSi() {
        return accountSi;
    }

    public void setAccountSi(Long accountSi) {
        this.accountSi = accountSi;
    }

    @Column(name = "account_id")
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Column(name = "account_name")
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

}
