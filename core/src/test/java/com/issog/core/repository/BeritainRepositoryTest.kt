package com.issog.core.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.issog.core.data.BeritainRepository
import com.issog.core.data.Resources
import com.issog.core.data.source.local.room.entites.ArticleEntity
import com.issog.core.data.source.remote.request.NewsRequest
import com.issog.core.data.source.remote.response.SourceResponse
import com.issog.core.data.source.remote.response.TopHeadlineResponse
import com.issog.core.domain.usecase.BeritainCategory
import com.issog.core.source.FakeLocalDataSource
import com.issog.core.source.FakeRemoteDataSource
import com.issog.core.utils.DataMapper.mapArticleEntityToModel
import com.issog.core.utils.DataMapper.mapArticleResponseToModel
import com.issog.core.utils.DataMapper.mapSourceResponseToModel
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BeritainRepositoryTest {

    private lateinit var beritainRepository: BeritainRepository

    @Before
    fun before() {
        beritainRepository = BeritainRepository(
            FakeRemoteDataSource(),
            FakeLocalDataSource()
        )
    }

    @Test
    fun `success get news source with name is equal`() = runTest {
        val expected = Resources.Success(
            listOf(
                SourceResponse.SourcesItem(
                    name = "Weather is good!"
                )
            ).mapSourceResponseToModel()
        )

        beritainRepository.getNewsSources().test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(expected)
            cancelAndConsumeRemainingEvents()
        }
    }


    @Test
    fun `success get article top headline with source name & title is equal`() = runTest {
        val expected = Resources.Success(
            listOf(
                TopHeadlineResponse.ArticlesItem(
                    title = "Good News Everybody",
                    source =  TopHeadlineResponse.Source(
                        "ABC News"
                    )
                )
            ).mapArticleResponseToModel()
        )

        beritainRepository.getTopHeadlineByCategory(NewsRequest()).test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(expected)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `success get favorite list length and equal to 3`() = runTest {
        val expected = Resources.Success(
            listOf(
                ArticleEntity(0),
                ArticleEntity(1),
                ArticleEntity(2)
            ).mapArticleEntityToModel()
        )

        beritainRepository.getFavoriteArticle().test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(expected)
            cancelAndConsumeRemainingEvents()
        }
    }
}