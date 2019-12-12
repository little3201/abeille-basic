/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.basic.assets.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import top.abeille.basic.assets.dto.AccountDTO;
import top.abeille.basic.assets.service.AccountInfoService;
import top.abeille.basic.assets.vo.AccountVO;
import top.abeille.common.basic.AbstractController;

import javax.validation.Valid;

/**
 * 账户信息Controller
 *
 * @author liwenqiang 2018/12/20 9:54
 **/
@RestController
@RequestMapping("/account")
public class AccountInfoController extends AbstractController {

    private final AccountInfoService accountInfoService;

    public AccountInfoController(AccountInfoService accountInfoService) {
        this.accountInfoService = accountInfoService;
    }

    /**
     * 查询账号信息——根据ID
     *
     * @param accountId 账户ID
     * @return Mono<AccountInfo>
     */
    @GetMapping("/{accountId}")
    public Mono<ResponseEntity<AccountVO>> getAccount(@PathVariable Long accountId) {
        return accountInfoService.queryById(accountId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * 保存账号信息
     *
     * @param account 账户信息
     * @return Mono<AccountInfo>
     */
    @PostMapping
    public Mono<ResponseEntity<AccountVO>> saveAccount(@RequestBody @Valid AccountDTO account) {
        return accountInfoService.create(account)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
    }

    /**
     * 修改账号信息
     *
     * @param account 账户信息
     * @return Mono<AccountInfo>
     */
    @PutMapping("/{accountId}")
    public Mono<ResponseEntity<AccountVO>> modifyAccount(@PathVariable Long accountId, @RequestBody @Valid AccountDTO account) {
        return accountInfoService.modify(accountId, account)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_MODIFIED));
    }

    /**
     * 删除账号信息
     *
     * @param accountId 主键
     * @return Mono<Void>
     */
    @DeleteMapping("/{accountId}")
    public Mono<ResponseEntity<Void>> removeAccount(@PathVariable Long accountId) {
        return accountInfoService.removeById(accountId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
    }
}
