/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.basic.hypervisor.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.abeille.basic.hypervisor.dto.SourceDTO;
import top.abeille.basic.hypervisor.service.ResourceService;
import top.abeille.basic.hypervisor.vo.ResourceVO;
import top.abeille.common.basic.AbstractController;

import javax.validation.Valid;

/**
 * 权限资源controller
 *
 * @author liwenqiang 2018/12/17 19:39
 **/
@RestController
@RequestMapping("/resource")
public class ResourceController extends AbstractController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    /**
     * 查询资源信息
     *
     * @return 如果查询到数据，返回查询到的分页后的信息列表，否则返回空
     */
    @GetMapping
    public Flux<ResourceVO> retrieveSource() {
        return resourceService.retrieveAll();
    }

    /**
     * 根据传入的数据添加信息
     *
     * @param sourceDTO 要添加的数据
     * @return 如果添加数据成功，返回添加后的信息，否则返回417状态码
     */
    @PostMapping
    public Mono<ResourceVO> createSource(@RequestBody @Valid SourceDTO sourceDTO) {
        return resourceService.create(sourceDTO);
    }

    /**
     * 根据传入的代码和要修改的数据，修改信息
     *
     * @param code      代码
     * @param sourceDTO 要修改的数据
     * @return 如果修改数据成功，返回修改后的信息，否则返回304状态码
     */
    @PutMapping("/{code}")
    public Mono<ResourceVO> modifySource(@PathVariable String code, @RequestBody @Valid SourceDTO sourceDTO) {
        return resourceService.modify(code, sourceDTO);
    }

    /**
     * 根据传入的代码查询信息
     *
     * @param code 代码
     * @return 如果查询到数据，返回查询到的信息，否则返回404状态码
     */
    @GetMapping("/{code}")
    public Mono<ResourceVO> fetchGroup(@PathVariable String code) {
        return resourceService.fetchByCode(code);
    }
}
