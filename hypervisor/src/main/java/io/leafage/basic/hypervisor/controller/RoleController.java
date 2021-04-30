/*
 * Copyright (c) 2021. Leafage All Right Reserved.
 */
package io.leafage.basic.hypervisor.controller;

import io.leafage.basic.hypervisor.dto.RoleDTO;
import io.leafage.basic.hypervisor.service.RoleService;
import io.leafage.basic.hypervisor.vo.RoleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * 角色信息接口
 *
 * @author liwenqiang 2018/12/17 19:38
 **/
@RestController
@RequestMapping("/role")
public class RoleController {

    private final Logger logger = LoggerFactory.getLogger(RoleController.class);

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 分页查询
     *
     * @param page 页码
     * @param size 大小
     * @return 如果查询到数据，返回查询到的分页后的信息列表，否则返回空
     */
    @GetMapping
    public ResponseEntity<Page<RoleVO>> retrieve(@RequestParam int page, @RequestParam int size, String order) {
        Page<RoleVO> roles = roleService.retrieves(page, size, order);
        if (!roles.hasContent()) {
            logger.info("Not found anything about role with pageable.");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(roles);
    }

    /**
     * 查询信息
     *
     * @param code 代码
     * @return 如果查询到数据，返回查询到的信息，否则返回204状态码
     */
    @GetMapping("/{code}")
    public ResponseEntity<RoleVO> fetch(@PathVariable String code) {
        RoleVO roleVO = roleService.fetch(code);
        if (Objects.isNull(roleVO)) {
            logger.info("Not found anything with code: {}.", code);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(roleVO);
    }

    /**
     * 添加信息
     *
     * @param roleDTO 要添加的数据
     * @return 如果添加数据成功，返回添加后的信息，否则返回417状态码
     */
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid RoleDTO roleDTO) {
        try {
            roleService.create(roleDTO);
        } catch (Exception e) {
            logger.error("Create role occurred an error: ", e);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改信息
     *
     * @param code    代码
     * @param roleDTO 要修改的数据
     * @return 如果修改数据成功，返回修改后的信息，否则返回304状态码
     */
    @PutMapping("/{code}")
    public ResponseEntity<Void> modify(@PathVariable String code, @RequestBody @Valid RoleDTO roleDTO) {
        try {
            roleService.modify(code, roleDTO);
        } catch (Exception e) {
            logger.error("Modify role occurred an error: ", e);
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * 删除信息
     *
     * @param code 代码
     * @return 如果删除成功，返回200状态码，否则返回417状态码
     */
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> remove(@PathVariable String code) {
        try {
            roleService.remove(code);
        } catch (Exception e) {
            logger.error("Remove role occurred an error: ", e);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok().build();
    }

}
