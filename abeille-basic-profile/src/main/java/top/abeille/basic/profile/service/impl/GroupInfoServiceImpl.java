/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.basic.profile.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import top.abeille.basic.profile.dao.GroupInfoDao;
import top.abeille.basic.profile.entity.GroupInfo;
import top.abeille.basic.profile.service.GroupInfoService;

import java.util.List;
import java.util.Optional;

/**
 * 组织信息Service实现
 *
 * @author liwenqiang 2018/12/17 19:25
 **/
@Service
public class GroupInfoServiceImpl implements GroupInfoService {

    private final GroupInfoDao groupInfoDao;

    public GroupInfoServiceImpl(GroupInfoDao groupInfoDao) {
        this.groupInfoDao = groupInfoDao;
    }

    @Override
    public GroupInfo getById(Long id) {
        Optional<GroupInfo> optional = groupInfoDao.findById(id);
        return optional.orElse(null);
    }

    @Override
    public Page<GroupInfo> findAllByPage(Integer pageNum, Integer pageSize) {
        return groupInfoDao.findAll(PageRequest.of(pageNum, pageSize));
    }

    @Override
    public GroupInfo save(GroupInfo entity) {
        return groupInfoDao.save(entity);
    }

    @Override
    public void removeById(Long id) {
        groupInfoDao.deleteById(id);
    }

    @Override
    public void removeInBatch(List<GroupInfo> entities) {
        groupInfoDao.deleteInBatch(entities);
    }
}
