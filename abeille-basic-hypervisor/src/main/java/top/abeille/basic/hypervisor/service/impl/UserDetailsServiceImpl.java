/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.basic.hypervisor.service.impl;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import top.abeille.basic.hypervisor.entity.RoleSource;
import top.abeille.basic.hypervisor.entity.UserInfo;
import top.abeille.basic.hypervisor.repository.RoleSourceRepository;
import top.abeille.basic.hypervisor.repository.UserInfoRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

/**
 * 用户认证service实现
 *
 * @author liwenqiang 2018/10/18 21:18
 **/
@Service
public class UserDetailsServiceImpl extends MapReactiveUserDetailsService {

    private final UserInfoRepository userInfoRepository;
    private final RoleSourceRepository roleSourceRepository;

    public UserDetailsServiceImpl(UserInfoRepository userInfoRepository, RoleSourceRepository roleSourceRepository) {
        this.userInfoRepository = userInfoRepository;
        this.roleSourceRepository = roleSourceRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return this.loadUserByUsername(username).dematerialize();
    }

    /**
     * 设置必要参数匹配条件
     *
     * @param username 账号
     * @return ExampleMatcher
     */
    private Mono<User> loadUserByUsername(String username) {
        UserInfo info = new UserInfo();
        if (isMobile(username)) {
            info.setMobile(username);
        } else if (isEmail(username)) {
            info.setEmail(username);
        } else {
            throw new UsernameNotFoundException(username);
        }
        // 组装查询条件，只查询可用，未被锁定的用户信息
        ExampleMatcher exampleMatcher = appendConditions();
        return userInfoRepository.findOne(Example.of(info, exampleMatcher)).map(userVO -> {
            Set<GrantedAuthority> authorities = new HashSet<>();
            List<RoleSource> roleSources = roleSourceRepository.findAllByRoleIdAndEnabled(userVO.getRoleId(), Boolean.TRUE);
            roleSources.forEach(roleSource -> authorities.add(new SimpleGrantedAuthority(String.valueOf(roleSource.getSourceId()))));
            return new User(isMobile(username) ? userVO.getMobile() : userVO.getEmail(), userVO.getPassword(),
                    userVO.getEnabled(), userVO.getAccountNonExpired(), userVO.getCredentialsNonExpired(),
                    userVO.getAccountNonLocked(), authorities);
        });
    }

    /**
     * 设置必要参数匹配条件
     *
     * @return ExampleMatcher
     */
    private ExampleMatcher appendConditions() {
        String[] fields = new String[]{"is_enabled", "is_credentials_non_expired", "is_account_non_locked", "is_account_non_expired"};
        ExampleMatcher matcher = ExampleMatcher.matching();
        for (String param : fields) {
            matcher.withMatcher(param, exact());
        }
        return matcher;
    }

    private boolean isMobile(String username) {
        return false;
    }

    private boolean isEmail(String username) {
        return false;
    }
}
