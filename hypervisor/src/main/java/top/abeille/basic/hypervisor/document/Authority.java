/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.basic.hypervisor.document;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * Model class for ResourceInfo
 *
 * @author liwenqiang 2020-10-06 22:09
 */
@Document(collection = "authority")
public class Authority {

    /**
     * 主键
     */
    @Id
    private String id;
    /**
     * 代码
     */
    @Indexed(unique = true)
    private String code;
    /**
     * 上级
     */
    private String superior;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private Type type;
    /**
     * 路径
     */
    private String path;
    /**
     * 请求方式, 如：GET、POST、PUT、DELETE等
     */
    private String mode;
    /**
     * 描述
     */
    private String description;
    /**
     * 是否有效
     */
    private boolean enabled = true;
    /**
     * 修改人
     */
    private String modifier;
    /**
     * 修改时间
     */
    @Field(value = "modify_time")
    private LocalDateTime modifyTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSuperior() {
        return superior;
    }

    public void setSuperior(String superior) {
        this.superior = superior;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type.val;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    enum Type {
        /**
         * 菜单
         */
        MENU(0, "menu"),
        /**
         * 按钮
         */
        BTN(1, "btn"),
        /**
         * tab页
         */
        TAB(2, "tab"),
        /**
         * 接口
         */
        @JsonEnumDefaultValue
        URL(3, "url");

        int code;
        String val;

        Type(int code, String val) {
            this.code = code;
            this.val = val;
        }
    }
}