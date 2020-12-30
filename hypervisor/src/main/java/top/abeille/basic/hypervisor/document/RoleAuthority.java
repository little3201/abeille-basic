/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.basic.hypervisor.document;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Model class for RoleResource
 *
 * @author liwenqiang 2020-10-06 22:09
 */
@Document(collection = "role_authority")
public class RoleAuthority extends BaseDocument {

    /**
     * 角色ID
     */
    @Indexed
    @Field(value = "role_id")
    private String roleId;
    /**
     * 资源ID
     */
    @Indexed
    @Field(value = "authority_id")
    private String authorityId;
    /**
     * 请求方式, 如：GET、POST、PUT、DELETE等
     */
    private String mode;


    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
