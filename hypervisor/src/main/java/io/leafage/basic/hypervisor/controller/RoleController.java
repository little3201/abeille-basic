/*
 * Copyright (c) 2021. Leafage All Right Reserved.
 */
package io.leafage.basic.hypervisor.controller;

import io.leafage.basic.hypervisor.document.RoleAuthority;
import io.leafage.basic.hypervisor.dto.RoleDTO;
import io.leafage.basic.hypervisor.service.RoleAuthorityService;
import io.leafage.basic.hypervisor.service.RoleService;
import io.leafage.basic.hypervisor.service.UserRoleService;
import io.leafage.basic.hypervisor.vo.AuthorityVO;
import io.leafage.basic.hypervisor.vo.RoleVO;
import io.leafage.basic.hypervisor.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.leafage.common.basic.TreeNode;

import javax.validation.Valid;
import java.util.Set;

/**
 * 角色信息controller
 *
 * @author liwenqiang 2018/12/17 19:38
 **/
@RestController
@RequestMapping("/role")
public class RoleController {

    private final Logger logger = LoggerFactory.getLogger(RoleController.class);

    private final UserRoleService userRoleService;
    private final RoleService roleService;
    private final RoleAuthorityService roleAuthorityService;

    public RoleController(UserRoleService userRoleService, RoleService roleService,
                          RoleAuthorityService roleAuthorityService) {
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.roleAuthorityService = roleAuthorityService;
    }

    /**
     * 查询角色信息
     *
     * @param page 页码
     * @param size 大小
     * @return 查询的数据集，异常时返回204状态码
     */
    @GetMapping
    public ResponseEntity<Flux<RoleVO>> retrieve(Integer page, Integer size) {
        Flux<RoleVO> voFlux;
        try {
            if (page == null || size == null) {
                voFlux = roleService.retrieve();
            } else {
                voFlux = roleService.retrieve(page, size);
            }
        } catch (Exception e) {
            logger.error("Retrieve role occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(voFlux);
    }

    /**
     * 查询树形数据
     *
     * @return 查询到的数据，否则返回空
     */
    @GetMapping("/tree")
    public ResponseEntity<Flux<TreeNode>> tree() {
        Flux<TreeNode> authorities;
        try {
            authorities = roleService.tree();
        } catch (Exception e) {
            logger.info("Retrieve role occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(authorities);
    }

    /**
     * 根据传入的代码查询信息
     *
     * @param code 代码
     * @return 查询的数据集，异常时返回204状态码
     */
    @GetMapping("/{code}")
    public ResponseEntity<Mono<RoleVO>> fetch(@PathVariable String code) {
        Mono<RoleVO> voMono;
        try {
            voMono = roleService.fetch(code);
        } catch (Exception e) {
            logger.error("Fetch role occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(voMono);
    }

    /**
     * 统计记录数
     *
     * @return 查询的数据集，异常时返回204状态码
     */
    @GetMapping("/count")
    public ResponseEntity<Mono<Long>> count() {
        Mono<Long> count;
        try {
            count = roleService.count();
        } catch (Exception e) {
            logger.error("Count role occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(count);
    }

    /**
     * 根据传入的数据添加信息
     *
     * @param roleDTO 要添加的数据
     * @return 添加后的信息，否则返回417状态码
     */
    @PostMapping
    public ResponseEntity<Mono<RoleVO>> create(@RequestBody @Valid RoleDTO roleDTO) {
        Mono<RoleVO> voMono;
        try {
            voMono = roleService.create(roleDTO);
        } catch (Exception e) {
            logger.error("Create role occurred an error: ", e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(voMono);
    }

    /**
     * 根据传入的代码和要修改的数据，修改信息
     *
     * @param code    代码
     * @param roleDTO 要修改的数据
     * @return 修改后的信息，否则返回304状态码
     */
    @PutMapping("/{code}")
    public ResponseEntity<Mono<RoleVO>> modify(@PathVariable String code, @RequestBody @Valid RoleDTO roleDTO) {
        Mono<RoleVO> voMono;
        try {
            voMono = roleService.modify(code, roleDTO);
        } catch (Exception e) {
            logger.error("Modify role occurred an error: ", e);
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        return ResponseEntity.accepted().body(voMono);
    }

    /**
     * 根据code查询关联用户信息
     *
     * @param code 角色code
     * @return 查询到的数据集，异常时返回204状态码
     */
    @GetMapping("/{code}/user")
    public ResponseEntity<Flux<UserVO>> users(@PathVariable String code) {
        Flux<UserVO> voFlux;
        try {
            voFlux = userRoleService.users(code);
        } catch (Exception e) {
            logger.error("Retrieve role users occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(voFlux);
    }

    /**
     * 查询角色-权限关联
     *
     * @param code 角色代码
     * @return 操作结果
     */
    @GetMapping("/{code}/authority")
    public ResponseEntity<Flux<AuthorityVO>> authorities(@PathVariable String code) {
        Flux<AuthorityVO> voFlux;
        try {
            voFlux = roleAuthorityService.authorities(code);
        } catch (Exception e) {
            logger.error("Relation role ah occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(voFlux);
    }

    /**
     * 保存角色-权限关联
     *
     * @param code        角色代码
     * @param authorities 权限信息
     * @return 操作结果
     */
    @PatchMapping("/{code}/authority")
    public ResponseEntity<Flux<RoleAuthority>> relation(@PathVariable String code, @RequestBody Set<String> authorities) {
        Flux<RoleAuthority> voFlux;
        try {
            voFlux = roleAuthorityService.relation(code, authorities);
        } catch (Exception e) {
            logger.error("Relation role ah occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.accepted().body(voFlux);
    }

}
