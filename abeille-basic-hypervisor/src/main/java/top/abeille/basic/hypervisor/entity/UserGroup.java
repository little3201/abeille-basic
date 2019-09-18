/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.basic.hypervisor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Model class for UserRole
 *
 * @author liwenqiang 2019/9/16 10:09
 **/
@Entity
@Table(name = "user_group")
public class UserGroup {

    /**
     * 主键
     */
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 用户主键
     */
    @NotNull
    @Column(name = "user_id")
    private Long userId;
    /**
     * 组主键
     */
    @NotNull
    @Column(name = "group_id")
    private Long groupId;

    /**
     * 是否有效
     */
    @JsonIgnore
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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