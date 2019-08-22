/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.basic.hypervisor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Model class for GroupInfo
 *
 * @author liwenqiang
 */
@Entity
@Table(name = "group_info")
public class GroupInfo {

    /**
     * 主键
     */
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    /**
     * 组ID
     */
    @NotNull
    @Column(name = "group_id")
    private String groupId;
    /**
     * 领导
     */
    @Column(name = "leader")
    private Long leader;
    /**
     * 上级
     */
    @Column(name = "superior")
    private Long superior;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Long getLeader() {
        return leader;
    }

    public void setLeader(Long leader) {
        this.leader = leader;
    }

    public Long getSuperior() {
        return superior;
    }

    public void setSuperior(Long superior) {
        this.superior = superior;
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
