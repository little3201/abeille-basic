/*
 * Copyright (c) 2021. Leafage All Right Reserved.
 */
package io.leafage.basic.hypervisor.service.impl;

import io.leafage.basic.hypervisor.dto.AuthorityDTO;
import io.leafage.basic.hypervisor.entity.Authority;
import io.leafage.basic.hypervisor.repository.AuthorityRepository;
import io.leafage.basic.hypervisor.service.AuthorityService;
import io.leafage.basic.hypervisor.vo.AuthorityVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.leafage.common.basic.AbstractBasicService;
import top.leafage.common.servlet.ServletTreeNode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 权限信息Service实现
 *
 * @author liwenqiang 2018/12/17 19:36
 **/
@Service
public class AuthorityServiceImpl extends AbstractBasicService implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Page<AuthorityVO> retrieve(int page, int size, String order) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(StringUtils.hasText(order) ? order : "modifyTime"));
        return authorityRepository.findAll(pageable).map(this::convertOuter);
    }

    @Override
    public List<ServletTreeNode> tree() {
        List<Authority> authorities = authorityRepository.findByTypeAndEnabledTrue('M');
        if (CollectionUtils.isEmpty(authorities)) {
            return Collections.emptyList();
        }
        return authorities.stream().filter(a -> a.getSuperior() == null).map(a -> {
            ServletTreeNode treeNode = this.constructNode(a.getCode(), a);
            Set<String> expand = new HashSet<>();
            expand.add("icon");
            expand.add("path");
            treeNode.setChildren(this.children(a, authorities, expand));
            return treeNode;
        }).collect(Collectors.toList());
    }

    @Override
    public AuthorityVO create(AuthorityDTO authorityDTO) {
        Authority authority = new Authority();
        BeanUtils.copyProperties(authorityDTO, authority);
        authority.setCode(this.generateCode());
        authority = authorityRepository.save(authority);
        return this.convertOuter(authority);
    }

    @Override
    public void remove(String code) {
        Authority authority = authorityRepository.getByCodeAndEnabledTrue(code);
        authorityRepository.deleteById(authority.getId());
    }

    /**
     * 转换对象
     *
     * @param info 基础对象
     * @return 结果对象
     */
    private AuthorityVO convertOuter(Authority info) {
        AuthorityVO authorityVO = new AuthorityVO();
        BeanUtils.copyProperties(info, authorityVO);
        return authorityVO;
    }

    /**
     * add child node
     *
     * @param superior    superior node
     * @param authorities to be build source data
     * @return tree node
     */
    private List<ServletTreeNode> addChildren(Authority superior, List<Authority> authorities) {
        return authorities.stream().filter(authority -> superior.getId().equals(authority.getSuperior()))
                .map(authority -> {
                    ServletTreeNode treeNode = this.constructNode(superior.getCode(), authority);
                    treeNode.setChildren(this.addChildren(authority, authorities));
                    return treeNode;
                }).collect(Collectors.toList());
    }

    /**
     * construct tree node
     *
     * @param superior  superior code
     * @param authority data
     * @return tree node
     */
    private ServletTreeNode constructNode(String superior, Authority authority) {
        ServletTreeNode treeNode = new ServletTreeNode(authority.getCode(), authority.getName());
        treeNode.setSuperior(superior);

        Map<String, String> expand = new HashMap<>();
        expand.put("icon", authority.getIcon());
        expand.put("path", authority.getPath());

        treeNode.setExpand(expand);
        return treeNode;
    }

}
