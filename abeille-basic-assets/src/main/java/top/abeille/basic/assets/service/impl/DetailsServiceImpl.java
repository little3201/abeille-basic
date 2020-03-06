/*
 * Copyright © 2010-2019 Abeille All rights reserved.
 */

package top.abeille.basic.assets.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import top.abeille.basic.assets.document.DetailsInfo;
import top.abeille.basic.assets.repository.DetailsRepository;
import top.abeille.basic.assets.service.DetailsService;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class DetailsServiceImpl implements DetailsService {
    /**
     * 开启日志
     */
    protected static final Logger logger = LoggerFactory.getLogger(DetailsServiceImpl.class);
    private final DetailsRepository detailsRepository;

    public DetailsServiceImpl(DetailsRepository detailsRepository) {
        this.detailsRepository = detailsRepository;
    }

    @Override
    public Mono<DetailsInfo> create(DetailsInfo detailsInfo) {
        detailsInfo.setEnabled(Boolean.TRUE);
        detailsInfo.setModifyTime(LocalDateTime.now());
        return detailsRepository.save(detailsInfo).doOnSuccess(content ->
                logger.info("结果：id-{}, content-{}", content.getId(), content.getContent()))
                .doOnError(error -> logger.error("新增异常"));
    }

    @Override
    public Mono<DetailsInfo> modify(String businessId, DetailsInfo detailsInfo) {
        return this.fetchByBusinessId(businessId).flatMap(content -> {
            BeanUtils.copyProperties(detailsInfo, content);
            content.setModifyTime(LocalDateTime.now());
            return detailsRepository.save(content);
        });
    }

    @Override
    public Mono<DetailsInfo> fetchByBusinessId(String businessId) {
        Objects.requireNonNull(businessId);
        DetailsInfo info = new DetailsInfo();
        info.setBusinessId(businessId);
        info.setEnabled(Boolean.TRUE);
        return detailsRepository.findOne(Example.of(info));
    }
}