/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.basic.assets.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.abeille.basic.assets.vo.UserInfoVO;

/**
 * 用户api
 *
 * @author liwenqiang 2019-03-03 22:55
 **/
@FeignClient(name = "abeille-basic-authority")
public interface HypervisorApi {

    /**
     * 根据userId获取用户信息
     *
     * @param userId 用户ID
     * @return UserInfoVO 用户信息
     */
    @GetMapping("/user")
    UserInfoVO getUserInfo(@RequestParam("userId") String userId);
}