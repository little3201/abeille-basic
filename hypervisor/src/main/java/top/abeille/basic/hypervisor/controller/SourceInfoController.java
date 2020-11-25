/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.basic.hypervisor.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import top.abeille.basic.hypervisor.dto.SourceDTO;
import top.abeille.basic.hypervisor.service.SourceService;
import top.abeille.basic.hypervisor.vo.SourceVO;
import top.abeille.common.basic.AbstractController;

import javax.validation.Valid;

/**
 * 权限资源接口
 *
 * @author liwenqiang 2018/12/17 19:39
 **/
@RestController
@RequestMapping("/source")
public class SourceInfoController extends AbstractController {

    private final SourceService sourceService;

    public SourceInfoController(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    /**
     * 分页查询翻译信息
     *
     * @param pageNum  当前页
     * @param pageSize 页内数据量
     * @return 如果查询到数据，返回查询到的分页后的信息列表，否则返回空
     */
    @GetMapping
    public ResponseEntity<Object> retrieveSource(Integer pageNum, Integer pageSize) {
        Pageable pageable = super.initPageParams(pageNum, pageSize);
        Page<SourceVO> sources = sourceService.retrieveByPage(pageable);
        if (CollectionUtils.isEmpty(sources.getContent())) {
            logger.info("Not found anything about source with pageable.");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sources);
    }

    /**
     * 根据传入的数据添加信息
     *
     * @param sourceDTO 要添加的数据
     * @return 如果添加数据成功，返回添加后的信息，否则返回417状态码
     */
    @PostMapping
    public ResponseEntity<Object> createSource(@RequestBody @Valid SourceDTO sourceDTO) {
        try {
            sourceService.create(sourceDTO);
        } catch (Exception e) {
            logger.error("Save user occurred an error: ", e);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

}