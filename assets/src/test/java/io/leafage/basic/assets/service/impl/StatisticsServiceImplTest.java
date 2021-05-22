package io.leafage.basic.assets.service.impl;

import io.leafage.basic.assets.document.Posts;
import io.leafage.basic.assets.document.Statistics;
import io.leafage.basic.assets.repository.PostsRepository;
import io.leafage.basic.assets.repository.StatisticsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;

/**
 * 统计service测试
 *
 * @author liwenqiang 2021/5/22 20:50
 */
@ExtendWith(MockitoExtension.class)
class StatisticsServiceImplTest {

    @Mock
    private StatisticsRepository statisticsRepository;

    @Mock
    private PostsRepository postsRepository;

    @InjectMocks
    private StatisticsServiceImpl statisticsService;

    @Test
    void viewed() {
        given(this.statisticsRepository.getByDate(Mockito.any(LocalDate.class)))
                .willReturn(Mono.just(Mockito.mock(Statistics.class)));
        StepVerifier.create(statisticsService.viewed()).expectNextCount(1).verifyComplete();
    }

    @Test
    void create() {
        Posts posts = new Posts();
        posts.setViewed(12);
        posts.setLikes(23);
        posts.setComment(2);
        given(this.postsRepository.findByEnabledTrue()).willReturn(Flux.just(posts));
        given(this.statisticsRepository.getByDate(Mockito.any(LocalDate.class)))
                .willReturn(Mono.just(Mockito.mock(Statistics.class)));
        given(this.statisticsRepository.insert(Mockito.any(Statistics.class))).willReturn(Mono.just(Mockito.mock(Statistics.class)));
        StepVerifier.create(statisticsService.create()).expectNextCount(1).verifyComplete();
    }
}