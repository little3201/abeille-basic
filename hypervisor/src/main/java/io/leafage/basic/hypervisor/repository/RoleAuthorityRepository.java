/*
 * Copyright (c) 2021. Leafage All Right Reserved.
 */
package io.leafage.basic.hypervisor.repository;

import io.leafage.basic.hypervisor.entity.RoleAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色权限repository
 *
 * @author liwenqiang 2018/9/26 11:29
 **/
@Repository
public interface RoleAuthorityRepository extends JpaRepository<RoleAuthority, Long> {

    /**
     * 查询所有资源——根据角色ID集合
     *
     * @param roleIdList 角色ID集合
     * @return Flux
     */
    List<RoleAuthority> findByRoleIdIn(@NotNull List<Long> roleIdList);
}
