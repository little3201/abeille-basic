/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.basic.hypervisor.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Model class for UserInfo
 *
 * @author liwenqiang
 */
@Entity
@Table(name = "user_info")
public class UserInfo {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 业务ID
     */
    @Column(name = "business_id")
    private String businessId;
    /**
     * 昵称
     */
    @Column(name = "nickname")
    private String nickname;
    /**
     * 头像
     */
    @Column(name = "avatar")
    private String avatar;
    /**
     * 密码
     */
    @Column(name = "password")
    private String password;
    /**
     * 电话
     */
    @Column(name = "mobile")
    private String mobile;
    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;
    /**
     * 地址
     */
    @Column(name = "address")
    private String address;
    /**
     * 是否无效
     */
    @Column(name = "is_account_non_expired")
    private Boolean accountNonExpired;
    /**
     * 是否没有锁定
     */
    @Column(name = "is_account_non_locked")
    private Boolean accountNonLocked;
    /**
     * 密码是否有效
     */
    @Column(name = "is_credentials_non_expired")
    private Boolean credentialsNonExpired;
    /**
     * 是否可用
     */
    @Column(name = "is_enabled")
    private Boolean enabled;
    /**
     * 修改人
     */
    @Column(name = "modifier")
    private Long modifier;
    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private LocalDateTime modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getModifier() {
        return modifier;
    }

    public void setModifier(Long modifier) {
        this.modifier = modifier;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }
}