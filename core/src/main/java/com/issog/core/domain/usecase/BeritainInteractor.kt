package com.issog.core.domain.usecase

import com.issog.core.data.Resources
import com.issog.core.data.source.remote.request.NewsRequest
import com.issog.core.domain.model.ArticleModel
import com.issog.core.domain.model.SourceModel
import com.issog.core.domain.repository.IBeritainRepository
import com.issog.core.utils.DataMapper.mapArticleDomainToEntity
import kotlinx.coroutines.flow.Flow

class BeritainInteractor(private val repository: IBeritainRepository): BeritainUseCase {
    override fun getNewsSources(): Flow<Resources<List<SourceModel>>> {
        return repository.getNewsSources()
    }

    override fun getTopHeadlineByCategory(newsRequest: NewsRequest): Flow<Resources<List<ArticleModel>>> {
        return repository.getTopHeadlineByCategory(newsRequest)
    }

    override fun getFavoriteArticle(): Flow<Resources<List<ArticleModel>>> {
        return repository.getFavoriteArticle()
    }

    override suspend fun addFavoriteArticle(article: ArticleModel) {
        repository.addFavoriteArticle(article.mapArticleDomainToEntity())
    }

    override fun updateFavoriteArticle(article: ArticleModel) {
        repository.updateFavoriteArticle(article.mapArticleDomainToEntity())
    }
}