/*
 * Copyright (c) 2021. Leafage All Right Reserved.
 */
package io.leafage.basic.assets.service.impl;


import com.mongodb.client.result.UpdateResult;
import io.leafage.basic.assets.document.Category;
import io.leafage.basic.assets.document.Posts;
import io.leafage.basic.assets.document.PostsContent;
import io.leafage.basic.assets.dto.PostsDTO;
import io.leafage.basic.assets.repository.CategoryRepository;
import io.leafage.basic.assets.repository.PostsRepository;
import io.leafage.basic.assets.service.PostsContentService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.BDDMockito.given;

/**
 * 帖子service测试
 *
 * @author liwenqiang 2019/9/19 9:27
 */
@ExtendWith(MockitoExtension.class)
class PostsServiceImplTest {

    @Mock
    private PostsRepository postsRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PostsContentService postsContentService;

    @Mock
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @InjectMocks
    private PostsServiceImpl postsService;

    @Test
    void retrieve() {
        given(this.postsRepository.findByEnabledTrue()).willReturn(Flux.just(Mockito.mock(Posts.class)));
        StepVerifier.create(this.postsService.retrieve()).expectNextCount(1).verifyComplete();
    }

    @Test
    void retrieve_page() {
        Posts posts = new Posts();
        ObjectId categoryId = new ObjectId();
        posts.setCategoryId(categoryId);
        given(this.postsRepository.findByEnabledTrue(PageRequest.of(0, 2,
                Sort.by(Sort.Direction.DESC, "id")))).willReturn(Flux.just(posts));

        given(this.categoryRepository.getById(categoryId)).willReturn(Mono.just(Mockito.mock(Category.class)));

        StepVerifier.create(this.postsService.retrieve(0, 2, "id")).expectNextCount(1).verifyComplete();
    }

    @Test
    void retrieve_page_category() {
        Category category = new Category();
        ObjectId id = new ObjectId();
        category.setId(id);
        given(this.categoryRepository.getByCodeAndEnabledTrue(Mockito.anyString())).willReturn(Mono.just(category));

        given(this.postsRepository.findByCategoryIdAndEnabledTrue(id, PageRequest.of(0, 2,
                Sort.by(Sort.Direction.DESC, "id")))).willReturn(Flux.just(Mockito.mock(Posts.class)));

        StepVerifier.create(this.postsService.retrieve(0, 2, "21213G0J2", "id"))
                .expectNextCount(1).verifyComplete();
    }

    @Test
    void fetchDetails() {
        Posts posts = new Posts();
        ObjectId id = new ObjectId();
        posts.setId(id);
        ObjectId categoryId = new ObjectId();
        posts.setCategoryId(categoryId);
        given(this.postsRepository.getByCodeAndEnabledTrue(Mockito.anyString()))
                .willReturn(Mono.just(posts));

        given(this.reactiveMongoTemplate.upsert(Query.query(Criteria.where("id").is(id)),
                new Update().inc("viewed", 1), Posts.class))
                .willReturn(Mono.just(Mockito.mock(UpdateResult.class)));

        given(this.categoryRepository.getById(categoryId)).willReturn(Mono.just(Mockito.mock(Category.class)));

        given(this.postsContentService.fetchByPostsId(id)).willReturn(Mono.just(Mockito.mock(PostsContent.class)));

        StepVerifier.create(this.postsService.fetchDetails("21213G0J2")).expectNextCount(1).verifyComplete();
    }

    @Test
    void fetchDetails_empty() {
        given(this.postsRepository.getByCodeAndEnabledTrue(Mockito.anyString())).willReturn(Mono.empty());
        StepVerifier.create(this.postsService.fetchDetails("21213G0J2")).verifyError();
    }

    @Test
    void fetch() {
        Posts posts = new Posts();
        ObjectId categoryId = new ObjectId();
        posts.setCategoryId(categoryId);
        given(this.postsRepository.getByCodeAndEnabledTrue(Mockito.anyString())).willReturn(Mono.just(posts));

        given(this.categoryRepository.getById(Mockito.any(ObjectId.class))).willReturn(Mono.just(Mockito.mock(Category.class)));

        StepVerifier.create(postsService.fetch("21213G0J2")).expectNextCount(1).verifyComplete();
    }

    @Test
    void fetchContent() {
        Posts posts = new Posts();
        ObjectId id = new ObjectId();
        posts.setId(id);
        given(this.postsRepository.getByCodeAndEnabledTrue(Mockito.anyString())).willReturn(Mono.just(posts));

        given(this.postsContentService.fetchByPostsId(Mockito.any(ObjectId.class))).willReturn(Mono.just(Mockito.mock(PostsContent.class)));

        StepVerifier.create(postsService.fetchContent("21213G0J2")).expectNextCount(1).verifyComplete();
    }

    @Test
    void count() {
        given(this.postsRepository.count()).willReturn(Mono.just(2L));
        StepVerifier.create(postsService.count()).expectNextCount(1).verifyComplete();
    }

    @Test
    void create() {
        PostsDTO postsDTO = new PostsDTO();
        postsDTO.setCategory("21213G0J2");
        given(this.categoryRepository.getByCodeAndEnabledTrue(Mockito.anyString()))
                .willReturn(Mono.just(Mockito.mock(Category.class)));

        given(this.postsRepository.insert(Mockito.any(Posts.class))).willReturn(Mono.just(Mockito.mock(Posts.class)));

        given(this.postsContentService.create(Mockito.any(PostsContent.class))).willReturn(Mono.empty());

        StepVerifier.create(this.postsService.create(postsDTO)).expectNextCount(1).verifyComplete();
    }

    @Test
    void create_error() {
        PostsDTO postsDTO = new PostsDTO();
        postsDTO.setCategory("21213G0J2");
        given(this.categoryRepository.getByCodeAndEnabledTrue(Mockito.anyString())).willReturn(Mono.empty());

        StepVerifier.create(this.postsService.create(postsDTO)).verifyError();
    }

    @Test
    void create_error2() {
        PostsDTO postsDTO = new PostsDTO();
        postsDTO.setCategory("21213G0J2");
        given(this.categoryRepository.getByCodeAndEnabledTrue(Mockito.anyString()))
                .willReturn(Mono.just(Mockito.mock(Category.class)));

        given(this.postsRepository.insert(Mockito.any(Posts.class))).willReturn(Mono.empty());

        StepVerifier.create(this.postsService.create(postsDTO)).verifyError();
    }

    @Test
    void modify() {
        given(this.postsRepository.getByCodeAndEnabledTrue(Mockito.anyString())).willReturn(Mono.just(Mockito.mock(Posts.class)));

        given(this.categoryRepository.getByCodeAndEnabledTrue(Mockito.anyString()))
                .willReturn(Mono.just(Mockito.mock(Category.class)));

        Posts posts = new Posts();
        posts.setId(new ObjectId());
        given(this.postsRepository.save(Mockito.any(Posts.class))).willReturn(Mono.just(posts));

        given(this.postsContentService.fetchByPostsId(Mockito.any(ObjectId.class)))
                .willReturn(Mono.just(Mockito.mock(PostsContent.class)));

        given(this.postsContentService.modify(Mockito.any(ObjectId.class), Mockito.any(PostsContent.class)))
                .willReturn(Mono.empty());

        PostsDTO postsDTO = new PostsDTO();
        postsDTO.setCategory("21213G0J2");
        StepVerifier.create(this.postsService.modify("21213G0J2", postsDTO)).expectNextCount(1).verifyComplete();
    }

    @Test
    void remove() {
        Posts posts = new Posts();
        ObjectId id = new ObjectId();
        posts.setId(id);
        given(this.postsRepository.getByCodeAndEnabledTrue(Mockito.anyString())).willReturn(Mono.just(posts));

        given(this.reactiveMongoTemplate.upsert(Query.query(Criteria.where("id").is(id)),
                Update.update("enabled", false), Posts.class)).willReturn(Mono.just(Mockito.mock(UpdateResult.class)));

        StepVerifier.create(postsService.remove("21213G0J2")).verifyComplete();
    }

    @Test
    void nextPosts() {
        Posts posts = new Posts();
        ObjectId id = new ObjectId();
        posts.setId(id);
        given(this.postsRepository.getByCodeAndEnabledTrue(Mockito.anyString()))
                .willReturn(Mono.just(posts));

        given(this.postsRepository.findByIdGreaterThanAndEnabledTrue(Mockito.any(ObjectId.class),
                Mockito.any(PageRequest.class))).willReturn(Flux.just(Mockito.mock(Posts.class)));

        StepVerifier.create(postsService.nextPosts("21213G0J2")).expectNextCount(1).verifyComplete();
    }

    @Test
    void previousPosts() {
        Posts posts = new Posts();
        ObjectId id = new ObjectId();
        posts.setId(id);
        given(this.postsRepository.getByCodeAndEnabledTrue(Mockito.anyString()))
                .willReturn(Mono.just(posts));

        given(this.postsRepository.findByIdLessThanAndEnabledTrue(Mockito.any(ObjectId.class),
                Mockito.any(PageRequest.class))).willReturn(Flux.just(Mockito.mock(Posts.class)));

        StepVerifier.create(postsService.previousPosts("21213G0J2")).expectNextCount(1).verifyComplete();
    }

    @Test
    void incrementLikes() {
        given(this.reactiveMongoTemplate.upsert(Query.query(Criteria.where("code").is("21213G0J2")),
                new Update().inc("likes", 1), Posts.class)).willReturn(Mono.just(Mockito.mock(UpdateResult.class)));

        given(this.postsRepository.getByCodeAndEnabledTrue(Mockito.anyString())).willReturn(Mono.just(Mockito.mock(Posts.class)));

        StepVerifier.create(postsService.incrementLikes("21213G0J2")).expectNextCount(1).verifyComplete();
    }

    @Test
    void search() {
        given(this.postsRepository.findByTitleIgnoreCaseLikeAndEnabledTrue(Mockito.anyString())).willReturn(Flux.just(Mockito.mock(Posts.class)));
        StepVerifier.create(postsService.search("leafag")).expectNextCount(1).verifyComplete();
    }

}