/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.basic.hypervisor.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.abeille.basic.hypervisor.document.RoleAuthority;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色权限dao接口
 *
 * @author liwenqiang 2018/9/26 11:29
 **/
@Repository
public interface RoleAuthorityRepository extends ReactiveCrudRepository<RoleAuthority, String> {

    /**
     * 查询所有资源——根据角色ID集合
     *
     * @param roleIdList 角色ID集合
     * @return Flux
     */
    Flux<RoleAuthority> findByRoleIdIn(@NotNull List<String> roleIdList);

    /**
     * 统计关联角色
     *
     * @param authorityId 权限ID
     * @return 用户数
     */
    Mono<Long> countByAuthorityIdAndEnabledTrue(@NotNull String authorityId);
}
