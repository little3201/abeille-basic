/*
 * Copyright (c) 2021. Leafage All Right Reserved.
 */
package io.leafage.basic.assets.controller;

import io.leafage.basic.assets.dto.PostsDTO;
import io.leafage.basic.assets.service.PostsService;
import io.leafage.basic.assets.vo.ContentVO;
import io.leafage.basic.assets.vo.PostsContentVO;
import io.leafage.basic.assets.vo.PostsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import javax.validation.Valid;

/**
 * 帖子信息controller
 *
 * @author liwenqiang 2018/12/20 9:54
 **/
@RestController
@RequestMapping("/posts")
public class PostsController {

    private final Logger logger = LoggerFactory.getLogger(PostsController.class);

    private final PostsService postsService;

    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    /**
     * 分页查询信息
     *
     * @param page     页码
     * @param size     大小
     * @param category 分类
     * @param order    排序字段
     * @return 查询到数据集，异常时返回204
     */
    @GetMapping
    public ResponseEntity<Flux<PostsVO>> retrieve(Integer page, Integer size, String category, String order) {
        Flux<PostsVO> voFlux;
        try {
            if (page == null || size == null) {
                voFlux = postsService.retrieve();
            } else if (StringUtils.hasText(category)) {
                voFlux = postsService.retrieve(page, size, category, order);
            } else {
                voFlux = postsService.retrieve(page, size, order);
            }
        } catch (Exception e) {
            logger.error("Retrieve posts occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(voFlux);
    }

    /**
     * 查询信息
     *
     * @param keyword 关键字
     * @return 查询到数据集，异常时返回204
     */
    @GetMapping("/search")
    public ResponseEntity<Flux<PostsVO>> search(@RequestParam String keyword) {
        Flux<PostsVO> voFlux;
        try {
            voFlux = postsService.search(keyword);
        } catch (Exception e) {
            logger.error("Search posts occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(voFlux);
    }

    /**
     * 根据传入的代码查询信息
     *
     * @param code 代码
     * @return 查询到数据，异常时返回204
     */
    @GetMapping("/{code}/details")
    public ResponseEntity<Mono<PostsContentVO>> fetchDetails(@PathVariable String code) {
        Mono<PostsContentVO> voMono;
        try {
            voMono = postsService.fetchDetails(code);
        } catch (Exception e) {
            logger.error("Fetch posts details occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(voMono);
    }

    /**
     * 根据传入的代码查询信息
     *
     * @param code 代码
     * @return 查询到数据，异常时返回204
     */
    @GetMapping("/{code}")
    public ResponseEntity<Mono<PostsVO>> fetch(@PathVariable String code) {
        Mono<PostsVO> voMono;
        try {
            voMono = postsService.fetch(code);
        } catch (Exception e) {
            logger.error("Fetch posts occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(voMono);
    }

    /**
     * 根据传入的代码查询信息
     *
     * @param code 代码
     * @return 查询到数据，异常时返回204
     */
    @GetMapping("/{code}/content")
    public ResponseEntity<Mono<ContentVO>> fetchContent(@PathVariable String code) {
        Mono<ContentVO> voMono;
        try {
            voMono = postsService.fetchContent(code);
        } catch (Exception e) {
            logger.error("Fetch posts occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(voMono);
    }

    /**
     * 统计记录数
     *
     * @return 查询到数据，异常时返回204
     */
    @GetMapping("/count")
    public ResponseEntity<Mono<Long>> count() {
        Mono<Long> count;
        try {
            count = postsService.count();
        } catch (Exception e) {
            logger.error("Count posts occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(count);
    }

    /**
     * 根据传入的代码查询下一条记录
     *
     * @param code 代码
     * @return 查询到数据，异常时返回204
     */
    @GetMapping("/{code}/next")
    public ResponseEntity<Mono<PostsVO>> fetchNext(@PathVariable String code) {
        Mono<PostsVO> voMono;
        try {
            voMono = postsService.nextPosts(code);
        } catch (Exception e) {
            logger.error("Fetch next posts occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(voMono);
    }

    /**
     * 根据传入的代码查询上一条记录
     *
     * @param code 代码
     * @return 查询到数据，异常时返回204
     */
    @GetMapping("/{code}/previous")
    public ResponseEntity<Mono<PostsVO>> fetchPrevious(@PathVariable String code) {
        Mono<PostsVO> voMono;
        try {
            voMono = postsService.previousPosts(code);
        } catch (Exception e) {
            logger.error("Fetch previous posts occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(voMono);
    }

    /**
     * 自增likes
     *
     * @param code 代码
     * @return 操作后的信息，否则返回417状态码
     */
    @PatchMapping("/{code}/like")
    public ResponseEntity<Mono<Integer>> incrementLikes(@PathVariable String code) {
        Mono<Integer> voMono;
        try {
            voMono = postsService.incrementLikes(code);
        } catch (Exception e) {
            logger.error("Increment posts like occurred an error: ", e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return ResponseEntity.ok(voMono);
    }

    /**
     * 根据传入的数据添加信息
     *
     * @param postsDTO 要添加的数据
     * @return 添加后的信息，否则返回417状态码
     */
    @PostMapping
    public ResponseEntity<Mono<PostsVO>> create(@RequestBody @Valid PostsDTO postsDTO) {
        Mono<PostsVO> voMono;
        try {
            voMono = postsService.create(postsDTO);
        } catch (Exception e) {
            logger.error("Create posts occurred an error: ", e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(voMono);
    }

    /**
     * 根据传入的数据修改信息
     *
     * @param code     代码
     * @param postsDTO 要修改的数据
     * @return 修改后的信息，否则返回304状态码
     */
    @PutMapping("/{code}")
    public ResponseEntity<Mono<PostsVO>> modify(@PathVariable String code, @RequestBody @Valid PostsDTO postsDTO) {
        Mono<PostsVO> voMono;
        try {
            voMono = postsService.modify(code, postsDTO);
        } catch (Exception e) {
            logger.error("Modify posts occurred an error: ", e);
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        return ResponseEntity.accepted().body(voMono);
    }

}
