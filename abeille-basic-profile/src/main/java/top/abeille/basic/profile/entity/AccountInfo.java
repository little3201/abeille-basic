/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.basic.profile.entity;

import top.abeille.common.basic.BasicInfo;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Model class for AccountInfo
 *
 * @author liwenqiang
 */
@Entity
@Table(name = "account_info")
public class AccountInfo extends BasicInfo {

    /**
     * 主键
     */
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;
    /**
     * 账户ID
     */
    @Column(name = "account_id")
    private String accountId;
    /**
     * 账户号码
     */
    @Column(name = "account_code")
    private Long accountCode;
    /**
     * 账户余额
     */
    @Column(name = "account_balance")
    private BigDecimal accountBalance;
    /**
     * 账户类型
     */
    @Column(name = "account_type")
    private String accountType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Long getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(Long accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

}