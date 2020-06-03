/*
 * Copyright © 2010-2019 Everyday Chain. All rights reserved.
 */
package top.abeille.basic.assets.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.abeille.basic.assets.dto.ArticleDTO;
import top.abeille.basic.assets.service.ArticleService;
import top.abeille.basic.assets.vo.ArticleDetailsVO;
import top.abeille.basic.assets.vo.ArticleVO;
import top.abeille.common.basic.AbstractController;

import javax.validation.Valid;

/**
 * 文章信息controller
 *
 * @author liwenqiang 2018/12/20 9:54
 **/
@RestController
@RequestMapping("/article")
public class ArticleController extends AbstractController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 分页查询翻译信息
     *
     * @return 如果查询到数据，返回查询到的分页后的信息列表，否则返回空
     */
    @GetMapping
    public Flux<ArticleVO> retrieveArticle() {
        return articleService.retrieveAll();
    }

    /**
     * 根据传入的业务id: businessId 查询信息
     *
     * @param businessId 业务id
     * @return 如果查询到数据，返回查询到的信息，否则返回404状态码
     */
    @GetMapping("/{businessId}")
    public Mono<ResponseEntity<ArticleDetailsVO>> fetchArticle(@PathVariable String businessId) {
        return articleService.fetchDetailsByBusinessId(businessId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    /**
     * 根据传入的数据添加信息
     *
     * @param articleDTO 要添加的数据
     * @return 如果添加数据成功，返回添加后的信息，否则返回417状态码
     */
    @PostMapping
    public Mono<ResponseEntity<ArticleVO>> createArticle(@RequestBody @Valid ArticleDTO articleDTO) {
        return articleService.create(articleDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
    }

    /**
     * 根据传入的业务id: businessId 和要修改的数据，修改信息
     *
     * @param businessId 业务id
     * @param articleDTO 要修改的数据
     * @return 如果修改数据成功，返回修改后的信息，否则返回304状态码
     */
    @PutMapping("/{businessId}")
    public Mono<ResponseEntity<ArticleVO>> modifyArticle(@PathVariable String businessId, @RequestBody @Valid ArticleDTO articleDTO) {
        return articleService.modify(businessId, articleDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_MODIFIED));
    }

}