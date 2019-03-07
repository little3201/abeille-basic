package top.abeille.basic.data.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.abeille.basic.data.model.UserInfoModel;
import top.abeille.basic.data.service.UserInfoService;
import top.abeille.basic.data.view.UserView;
import top.abeille.common.basic.BasicController;

/**
 * 用户信息Controller
 *
 * @author liwenqiang 2018/8/2 21:02
 **/
@Api(tags = "User Service Api")
@RequestMapping("/user/v1")
@RestController
public class UserInfoController extends BasicController {

    private final UserInfoService userInfoService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    /**
     * 用户查询——分页
     *
     * @param curPage  当前页
     * @param pageSize 页内数据量
     * @return ResponseEntity
     */
    @ApiOperation(value = "Fetch enabled users with pageable")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public ResponseEntity findUsers(Integer curPage, Integer pageSize) {
        if (curPage == null || pageSize == null) {
            return ResponseEntity.ok(HttpStatus.NOT_ACCEPTABLE);
        }
        Page<UserInfoModel> page = userInfoService.findAllByPage(curPage, pageSize);
        if (page == null) {
            log.info("Not found anything of user with pageable.");
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(page);
    }

    /**
     * 用户查询——根据ID
     *
     * @param id 主键
     * @return ResponseEntity
     */
    @ApiOperation(value = "Get single user by id")
    @ApiImplicitParam(name = "id", required = true, dataType = "Long")
    @GetMapping("/option")
    @JsonView(UserView.Details.class)
    public ResponseEntity getOption(Long id) {
        if (id == null) {
            return ResponseEntity.ok(HttpStatus.NOT_ACCEPTABLE);
        }
        UserInfoModel user = userInfoService.getById(id);
        if (user == null) {
            log.info("Not found anything of user with id: {}." + id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(user);
    }

    /**
     * 保存用户
     *
     * @param user 用户
     * @return ResponseEntity
     */
    @ApiOperation(value = "Save single user")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping("/option")
    public ResponseEntity saveOption(@RequestBody UserInfoModel user) {
        try {
            userInfoService.save(user);
        } catch (Exception e) {
            log.error("Save user occurred an error: {}", e);
            return ResponseEntity.ok("error");
        }
        return ResponseEntity.ok("success");
    }

    /**
     * 编辑用户
     *
     * @param user 用户
     * @return ResponseEntity
     */
    @ApiOperation(value = "Modify single user")
    @PutMapping("/option")
    public ResponseEntity modifyOption(@RequestBody UserInfoModel user) {
        if (user.getId() == null) {
            return ResponseEntity.ok("Request param is null");
        }
        try {
            userInfoService.save(user);
        } catch (Exception e) {
            log.error("Modify user occurred an error: {}", e);
            return ResponseEntity.ok("error");
        }
        return ResponseEntity.ok("success");
    }

    /**
     * 删除用户——根据ID
     *
     * @param id 主键
     * @return ResponseEntity
     */
    @ApiOperation(value = "Remove single user")
    @ApiImplicitParam(name = "id", required = true, dataType = "Long")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping("/option")
    public ResponseEntity removeOption(Long id) {
        try {
            userInfoService.removeById(id);
        } catch (Exception e) {
            log.error("Remove user occurred an error: {}", e);
            return ResponseEntity.ok("error");
        }
        return ResponseEntity.ok("success");
    }
}
