/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.basic.assets.service.impl;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.abeille.basic.assets.entity.ArticleInfo;
import top.abeille.basic.assets.repository.ArticleInfoRepository;
import top.abeille.basic.assets.service.ArticleInfoService;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

/**
 * 文章信息service实现
 *
 * @author liwenqiang 2018/12/20 9:54
 **/
@Service
public class ArticleInfoServiceImpl implements ArticleInfoService {

    private final ArticleInfoRepository articleInfoRepository;

    public ArticleInfoServiceImpl(ArticleInfoRepository articleInfoRepository) {
        this.articleInfoRepository = articleInfoRepository;
    }

    @Override
    public Page<ArticleInfo> findAllByPage(Integer pageNum, Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher.withMatcher("is_enabled", exact());
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setEnabled(true);
        return articleInfoRepository.findAll(Example.of(articleInfo, exampleMatcher), pageable);
    }

    @Override
    public ArticleInfo getByExample(ArticleInfo articleInfo) {
        articleInfo.setEnabled(true);
        Optional<ArticleInfo> optional = articleInfoRepository.findOne(Example.of(articleInfo));
        return optional.orElse(null);
    }

    @Override
    public ArticleInfo getByArticleId(String articleId) {
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setArticleId(articleId);
        return getByExample(articleInfo);
    }

    @Override
    @Transactional
    public void removeById(Long id) {
        articleInfoRepository.deleteById(id);
    }

    @Override
    public void removeInBatch(List<ArticleInfo> entities) {
        articleInfoRepository.deleteInBatch(entities);
    }
}
