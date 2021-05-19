/*
 * Copyright (c) 2021. Leafage All Right Reserved.
 */
package io.leafage.basic.hypervisor.service;

import io.leafage.basic.hypervisor.domain.TreeNode;
import io.leafage.basic.hypervisor.dto.RoleDTO;
import io.leafage.basic.hypervisor.vo.RoleVO;
import reactor.core.publisher.Flux;
import top.leafage.common.reactive.ReactiveBasicService;

/**
 * 角色信息service
 *
 * @author liwenqiang 2018/9/27 14:18
 **/
public interface RoleService extends ReactiveBasicService<RoleDTO, RoleVO> {

    Flux<TreeNode> tree();
}
