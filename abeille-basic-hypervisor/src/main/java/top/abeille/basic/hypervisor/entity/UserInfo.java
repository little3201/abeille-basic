/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.basic.hypervisor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    /**
     * 用户ID
     */
    @NotNull
    @Column(name = "user_id")
    private String userId;
    /**
     * 组织主键
     */
    @Column(name = "group_id")
    private Long groupId;
    /**
     * 中文名
     */
    @Column(name = "chinese_name")
    private String chineseName;
    /**
     * 英文名
     */
    @Column(name = "english_name")
    private String englishName;
    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;
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
     * 是否有效
     */
    @Column(name = "is_enabled")
    private Boolean enabled;
    /**
     * 修改人
     */
    @JsonIgnore
    @Column(name = "modifier")
    private Long modifier;
    /**
     * 修改时间
     */
    @JsonIgnore
    @Column(name = "modify_time")
    private LocalDateTime modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
