/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.basic.authority.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import top.abeille.basic.authority.entity.RoleInfo;
import top.abeille.basic.authority.service.RoleInfoService;
import top.abeille.common.basic.AbstractController;

/**
 * 角色信息controller
 *
 * @author liwenqiang 2018/12/17 19:38
 **/
@Api(tags = "Role Api")
@RestController
public class RoleInfoController extends AbstractController {

    private final RoleInfoService roleInfoService;

    public RoleInfoController(RoleInfoService roleInfoService) {
        this.roleInfoService = roleInfoService;
    }

    /**
     * 角色查询——分页
     *
     * @param pageNum  当前页
     * @param pageSize 页内数据量
     * @return ResponseEntity
     */
    @ApiOperation(value = "Fetch enabled roles with pageable")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/roles")
    public ResponseEntity findRoles(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageSize == null) {
            return ResponseEntity.ok(HttpStatus.NOT_ACCEPTABLE);
        }
        Page<RoleInfo> roles = roleInfoService.findAllByPage(pageNum, pageSize);
        if (CollectionUtils.isEmpty(roles.getContent())) {
            log.info("Not found anything about role with pageable.");
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(roles);
    }

    /**
     * 角色查询——根据ID
     *
     * @param id 主键
     * @return ResponseEntity
     */
    @ApiOperation(value = "Get single role by id")
    @ApiImplicitParam(name = "id", required = true)
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/role/{id}")
    public ResponseEntity getRole(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.ok(HttpStatus.NOT_ACCEPTABLE);
        }
        RoleInfo role = roleInfoService.getById(id);
        if (role == null) {
            log.info("Not found anything of role with id: {}.", id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(role);
    }

    /**
     * 保存角色
     *
     * @param role 角色
     * @return ResponseEntity
     */
    @ApiOperation(value = "Save single role")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping("/role")
    public ResponseEntity saveRole(@RequestBody RoleInfo role) {
        try {
            roleInfoService.save(role);
        } catch (Exception e) {
            log.error("Save role occurred an error: ", e);
            return ResponseEntity.ok(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    /**
     * 编辑角色
     *
     * @param role 角色
     * @return ResponseEntity
     */
    @ApiOperation(value = "Modify single role")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PutMapping("/role")
    public ResponseEntity modifyRole(@RequestBody RoleInfo role) {
        if (role.getId() == null) {
            return ResponseEntity.ok(HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            roleInfoService.save(role);
        } catch (Exception e) {
            log.error("Modify role occurred an error: ", e);
            return ResponseEntity.ok(HttpStatus.NOT_MODIFIED);
        }
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    /**
     * 删除角色——根据ID
     *
     * @param id 主键
     * @return ResponseEntity
     */
    @ApiOperation(value = "Remove single role")
    @ApiImplicitParam(name = "id", required = true)
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping("/role/{id}")
    public ResponseEntity removeRole(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.ok(HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            roleInfoService.removeById(id);
        } catch (Exception e) {
            log.error("Remove role occurred an error: ", e);
            return ResponseEntity.ok(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
