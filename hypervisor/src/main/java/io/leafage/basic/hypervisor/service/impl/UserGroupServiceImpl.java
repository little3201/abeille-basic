package io.leafage.basic.hypervisor.service.impl;

import io.leafage.basic.hypervisor.document.UserGroup;
import io.leafage.basic.hypervisor.repository.GroupRepository;
import io.leafage.basic.hypervisor.repository.UserGroupRepository;
import io.leafage.basic.hypervisor.repository.UserRepository;
import io.leafage.basic.hypervisor.service.UserGroupService;
import io.leafage.basic.hypervisor.vo.GroupVO;
import io.leafage.basic.hypervisor.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.naming.NotContextException;
import java.util.Set;

@Service
public class UserGroupServiceImpl implements UserGroupService {

    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final GroupRepository groupRepository;

    public UserGroupServiceImpl(UserRepository userRepository, UserGroupRepository userGroupRepository,
                                GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public Flux<UserVO> users(String code) {
        return groupRepository.getByCodeAndEnabledTrue(code).switchIfEmpty(Mono.error(NotContextException::new))
                .flatMapMany(group -> userGroupRepository.findByGroupId(group.getId()).flatMap(userGroup ->
                        userRepository.findById(userGroup.getUserId()).map(user -> {
                            UserVO userVO = new UserVO();
                            BeanUtils.copyProperties(user, userVO);
                            return userVO;
                        }))
                );
    }

    @Override
    public Flux<GroupVO> groups(String username) {
        return userRepository.getByUsername(username).switchIfEmpty(Mono.error(NotContextException::new))
                .flatMapMany(user -> userGroupRepository.findByUserId(user.getId()).flatMap(userGroup ->
                        groupRepository.findById(userGroup.getGroupId()).map(group -> {
                            GroupVO groupVO = new GroupVO();
                            BeanUtils.copyProperties(group, groupVO);
                            return groupVO;
                        }))
                );
    }

    @Override
    public Flux<UserGroup> relation(String username, Set<String> groups) {
        return userRepository.getByUsername(username).switchIfEmpty(Mono.error(NotContextException::new))
                .flatMapMany(user -> {
                    UserGroup userGroup = new UserGroup();
                    userGroup.setUserId(user.getId());
                    return groupRepository.findByCodeInAndEnabledTrue(groups).map(group -> {
                        userGroup.setGroupId(group.getId());
                        return userGroup;
                    });
                }).collectList().flatMapMany(userGroupRepository::saveAll);
    }
}
