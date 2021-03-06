/*
 * Copyright (c) 2021. Leafage All Right Reserved.
 */
package io.leafage.basic.hypervisor.service.impl;

import io.leafage.basic.hypervisor.document.Role;
import io.leafage.basic.hypervisor.dto.RoleDTO;
import io.leafage.basic.hypervisor.repository.RoleRepository;
import io.leafage.basic.hypervisor.repository.UserRoleRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.BDDMockito.given;

/**
 * role接口测试
 *
 * @author liwenqiang 2019/1/29 17:10
 **/
@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void retrieve() {
        Role role = new Role();
        role.setId(new ObjectId());
        role.setSuperior(new ObjectId());
        given(this.roleRepository.findByEnabledTrue()).willReturn(Flux.just(role));

        given(this.userRoleRepository.countByRoleIdAndEnabledTrue(Mockito.any(ObjectId.class))).willReturn(Mono.just(2L));

        given(this.roleRepository.findById(Mockito.any(ObjectId.class))).willReturn(Mono.just(role));
        StepVerifier.create(roleService.retrieve()).expectNextCount(1).verifyComplete();
    }

    @Test
    void retrieve_page() {
        Role role = new Role();
        role.setId(new ObjectId());
        role.setSuperior(new ObjectId());
        given(this.roleRepository.findByEnabledTrue(PageRequest.of(0, 2))).willReturn(Flux.just(role));

        given(this.userRoleRepository.countByRoleIdAndEnabledTrue(Mockito.any(ObjectId.class))).willReturn(Mono.just(2L));

        role.setName("test");
        given(this.roleRepository.findById(Mockito.any(ObjectId.class))).willReturn(Mono.just(role));

        StepVerifier.create(roleService.retrieve(0, 2)).expectNextCount(1).verifyComplete();
    }

    @Test
    void fetch() {
        Role role = new Role();
        role.setId(new ObjectId());
        role.setSuperior(new ObjectId());
        given(this.roleRepository.getByCodeAndEnabledTrue(Mockito.anyString())).willReturn(Mono.just(role));

        given(this.roleRepository.findById(Mockito.any(ObjectId.class))).willReturn(Mono.just(Mockito.mock(Role.class)));

        StepVerifier.create(roleService.fetch("21612OL34")).expectNextCount(1).verifyComplete();
    }

    @Test
    void create() {
        given(this.roleRepository.getByCodeAndEnabledTrue(Mockito.anyString())).willReturn(Mono.just(Mockito.mock(Role.class)));

        Role role = new Role();
        role.setId(new ObjectId());
        role.setSuperior(new ObjectId());
        given(this.roleRepository.insert(Mockito.any(Role.class))).willReturn(Mono.just(role));

        given(this.userRoleRepository.countByRoleIdAndEnabledTrue(Mockito.any(ObjectId.class))).willReturn(Mono.just(2L));

        given(this.roleRepository.findById(Mockito.any(ObjectId.class))).willReturn(Mono.just(role));

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setSuperior("21612OL34");
        StepVerifier.create(roleService.create(roleDTO)).expectNextCount(1).verifyComplete();
    }

    @Test
    void modify() {
        given(this.roleRepository.getByCodeAndEnabledTrue(Mockito.anyString())).willReturn(Mono.just(Mockito.mock(Role.class)));

        Role role = new Role();
        role.setId(new ObjectId());
        role.setName("test");
        role.setSuperior(new ObjectId());
        given(this.roleRepository.save(Mockito.any(Role.class))).willReturn(Mono.just(role));

        given(this.userRoleRepository.countByRoleIdAndEnabledTrue(Mockito.any(ObjectId.class))).willReturn(Mono.just(2L));

        given(this.roleRepository.findById(Mockito.any(ObjectId.class))).willReturn(Mono.just(role));

        RoleDTO roleDTO = new RoleDTO();
        StepVerifier.create(roleService.modify("21612OL34", roleDTO)).expectNextCount(1).verifyComplete();
    }

    @Test
    void tree() {
        Role role = new Role();
        ObjectId id = new ObjectId();
        role.setId(id);
        role.setCode("21612OL35");
        role.setName("test");

        Role child = new Role();
        child.setId(new ObjectId());
        child.setSuperior(id);
        child.setCode("21612OL34");
        child.setName("test-sub");
        given(this.roleRepository.findByEnabledTrue()).willReturn(Flux.just(role, child));

        StepVerifier.create(roleService.tree()).expectNextCount(1).verifyComplete();
    }

    @Test
    void count() {
        given(this.roleRepository.count()).willReturn(Mono.just(2L));
        StepVerifier.create(roleService.count()).expectNextCount(1).verifyComplete();
    }
}