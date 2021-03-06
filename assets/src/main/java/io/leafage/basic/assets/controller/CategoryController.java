/*
 * Copyright (c) 2021. Leafage All Right Reserved.
 */
package io.leafage.basic.assets.controller;

import io.leafage.basic.assets.dto.CategoryDTO;
import io.leafage.basic.assets.service.CategoryService;
import io.leafage.basic.assets.vo.CategoryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * 类目信息controller
 *
 * @author liwenqiang 2020/2/16 14:26
 **/
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 列表查询
     *
     * @param page 分页位置
     * @param size 分页大小
     * @return 查询到数据集，异常时返回204
     */
    @GetMapping
    public ResponseEntity<Flux<CategoryVO>> retrieve(Integer page, Integer size) {
        Flux<CategoryVO> voFlux;
        try {
            if (page == null || size == null) {
                voFlux = categoryService.retrieve();
            } else {
                voFlux = categoryService.retrieve(page, size);
            }
        } catch (Exception e) {
            logger.error("Retrieve category occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(voFlux);
    }

    /**
     * 查询信息
     *
     * @param code 代码
     * @return 查询到数据，异常时返回204
     */
    @GetMapping("/{code}")
    public ResponseEntity<Mono<CategoryVO>> fetch(@PathVariable String code) {
        Mono<CategoryVO> voMono;
        try {
            voMono = categoryService.fetch(code);
        } catch (Exception e) {
            logger.error("Fetch category occurred an error: ", e);
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
            count = categoryService.count();
        } catch (Exception e) {
            logger.error("Count category occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(count);
    }

    /**
     * 根据传入的数据添加信息
     *
     * @param categoryDTO 要添加的数据
     * @return 添加后的信息，异常时返回417状态码
     */
    @PostMapping
    public ResponseEntity<Mono<CategoryVO>> create(@RequestBody @Valid CategoryDTO categoryDTO) {
        Mono<CategoryVO> voMono;
        try {
            voMono = categoryService.create(categoryDTO);
        } catch (Exception e) {
            logger.error("Create category occurred an error: ", e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(voMono);
    }

    /**
     * 根据传入的代码和要修改的数据，修改信息
     *
     * @param code        代码
     * @param categoryDTO 要修改的数据
     * @return 修改后的信息，异常时返回304状态码
     */
    @PutMapping("/{code}")
    public ResponseEntity<Mono<CategoryVO>> modify(@PathVariable String code, @RequestBody @Valid CategoryDTO categoryDTO) {
        Mono<CategoryVO> voMono;
        try {
            voMono = categoryService.modify(code, categoryDTO);
        } catch (Exception e) {
            logger.error("Modify category occurred an error: ", e);
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        return ResponseEntity.accepted().body(voMono);
    }

    /**
     * 删除信息
     *
     * @param code 代码
     * @return 200状态码，异常时返回417状态码
     */
    @DeleteMapping("/{code}")
    public ResponseEntity<Mono<Void>> remove(@PathVariable String code) {
        try {
            categoryService.remove(code);
        } catch (Exception e) {
            logger.error("Remove category occurred an error: ", e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return ResponseEntity.ok().build();
    }

}
