package top.abeille.basic.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import top.abeille.basic.data.dao.IUserRoleDao;
import top.abeille.basic.data.model.UserRoleModel;
import top.abeille.basic.data.service.IUserRoleService;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

/**
 * 用户角色Service实现
 *
 * @author liwenqiang
 * @date 2018-12-06 22:05
 **/
@Service
public class UserRoleServiceImpl implements IUserRoleService {

    private final IUserRoleDao userRoleDao;

    @Autowired
    public UserRoleServiceImpl(IUserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    @Override
    public List<UserRoleModel> findAllByExample(UserRoleModel userRole, ExampleMatcher exampleMatcher) {
        Example<UserRoleModel> example = Example.of(userRole, exampleMatcher);
        return userRoleDao.findAll(example);
    }

    @Override
    public void removeById(Long id) {
        userRoleDao.deleteById(id);
    }

    @Override
    public void removeInBatch(List<UserRoleModel> entities) {
        userRoleDao.deleteInBatch(entities);
    }

    @Override
    public List<UserRoleModel> findAllByUserId(Long userId) {
        UserRoleModel userRole = new UserRoleModel();
        userRole.setUserId(userId);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher(String.valueOf(userId), exact());
        return this.findAllByExample(userRole, exampleMatcher);
    }
}