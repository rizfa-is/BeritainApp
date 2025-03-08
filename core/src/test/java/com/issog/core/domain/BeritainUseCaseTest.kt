package com.issog.core.domain

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.issog.core.data.Resources
import com.issog.core.data.source.local.room.entites.ArticleEntity
import com.issog.core.data.source.remote.request.NewsRequest
import com.issog.core.data.source.remote.response.SourceResponse
import com.issog.core.domain.model.ArticleModel
import com.issog.core.domain.usecase.BeritainCategory
import com.issog.core.domain.usecase.BeritainInteractor
import com.issog.core.domain.usecase.BeritainUseCase
import com.issog.core.repository.FakeBeritainRepository
import com.issog.core.utils.DataMapper.mapArticleEntityToModel
import com.issog.core.utils.DataMapper.mapSourceResponseToModel
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BeritainUseCaseTest {

    private lateinit var beritainUseCase: BeritainUseCase

    @Before
    fun before() {
        beritainUseCase = BeritainInteractor(FakeBeritainRepository())
    }

    @Test
    fun `success get news source with length equal to 3`() = runTest {
        val expected = Resources.Success(
            listOf(
                SourceResponse.SourcesItem(),
                SourceResponse.SourcesItem(),
                SourceResponse.SourcesItem()
            ).mapSourceResponseToModel()
        )

        beritainUseCase.getNewsSources().test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(expected)
            cancelAndConsumeRemainingEvents()
        }
    }


    @Test
    fun `error get article top headline with error code 401`() = runTest {
        val expected = Resources.Error<List<ArticleModel>>(code = 401, message = "")

        beritainUseCase.getTopHeadlineByCategory(NewsRequest()).test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(expected)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `success get favorite list with empty list`() = runTest {
        val expected = Resources.Success(
            listOf<ArticleEntity>().mapArticleEntityToModel()
        )

        beritainUseCase.getFavoriteArticle().test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(expected)
            cancelAndConsumeRemainingEvents()
        }
    }
}