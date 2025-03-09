package com.issog.beritainapp.domain

import com.issog.core.data.Resources
import com.issog.core.data.source.remote.request.NewsRequest
import com.issog.core.domain.model.ArticleModel
import com.issog.core.domain.model.SourceModel
import com.issog.core.domain.repository.IBeritainRepository
import com.issog.core.utils.DataMapper.mapArticleDomainToEntity
import kotlinx.coroutines.flow.Flow

class FakeBeritainUseCase(private val repository: IBeritainRepository) {
    fun getNewsSources(): Flow<Resources<List<SourceModel>>> {
        return repository.getNewsSources()
    }

    fun getTopHeadlineByCategory(newsRequest: NewsRequest): Flow<Resources<List<ArticleModel>>> {
        return repository.getTopHeadlineByCategory(newsRequest)
    }

    fun getFavoriteArticle(): Flow<Resources<List<ArticleModel>>> {
        return repository.getFavoriteArticle()
    }

    suspend fun addFavoriteArticle(article: ArticleModel) {
        repository.addFavoriteArticle(article.mapArticleDomainToEntity())
    }

    fun updateFavoriteArticle(article: ArticleModel) {
        repository.updateFavoriteArticle(article.mapArticleDomainToEntity())
    }
}