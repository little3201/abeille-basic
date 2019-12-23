/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.basic.hypervisor.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.abeille.basic.hypervisor.dto.RoleDTO;
import top.abeille.basic.hypervisor.entity.RoleInfo;
import top.abeille.basic.hypervisor.repository.RoleInfoRepository;
import top.abeille.basic.hypervisor.service.RoleInfoService;
import top.abeille.basic.hypervisor.vo.RoleVO;

/**
 * 角色信息service 实现
 *
 * @author liwenqiang 2018/9/27 14:20
 **/
@Service
public class RoleInfoServiceImpl implements RoleInfoService {

    private final RoleInfoRepository roleInfoRepository;

    public RoleInfoServiceImpl(RoleInfoRepository roleInfoRepository) {
        this.roleInfoRepository = roleInfoRepository;
    }

    @Override
    public Flux<RoleVO> retrieveByExample(RoleDTO enter, ExampleMatcher exampleMatcher) {
        // 创建查询模板实例
        RoleInfo info = new RoleInfo();
        BeanUtils.copyProperties(enter, info);
        return roleInfoRepository.findAll(Example.of(info, exampleMatcher)).map(roleInfo1 -> {
            RoleVO outer = new RoleVO();
            BeanUtils.copyProperties(roleInfo1, outer);
            return outer;
        });
    }

    @Override
    public Mono<RoleVO> fetchById(Long roleId) {
        RoleInfo info = new RoleInfo();
        info.setRoleId(roleId);
        return roleInfoRepository.findOne(Example.of(info)).map(role -> {
            RoleVO outer = new RoleVO();
            BeanUtils.copyProperties(role, outer);
            return outer;
        });
    }

    @Override
    public Mono<RoleVO> create(RoleDTO enter) {
        RoleInfo info = new RoleInfo();
        BeanUtils.copyProperties(enter, info);
        return roleInfoRepository.save(info).map(role -> {
            RoleVO outer = new RoleVO();
            BeanUtils.copyProperties(role, outer);
            return outer;
        });
    }

    @Override
    public Mono<RoleVO> modify(Long roleId, RoleDTO roleDTO) {
        RoleInfo info = new RoleInfo();
        BeanUtils.copyProperties(roleDTO, info);
        info.setRoleId(roleId);
        return roleInfoRepository.save(info).map(role -> {
            RoleVO outer = new RoleVO();
            BeanUtils.copyProperties(role, outer);
            return outer;
        });
    }
}
