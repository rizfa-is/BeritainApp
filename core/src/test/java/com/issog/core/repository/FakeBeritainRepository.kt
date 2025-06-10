package com.issog.core.repository

import com.issog.core.data.Resources
import com.issog.core.data.source.local.room.entites.ArticleEntity
import com.issog.core.data.source.remote.request.NewsRequest
import com.issog.core.domain.model.ArticleModel
import com.issog.core.domain.model.SourceModel
import com.issog.core.domain.repository.IBeritainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeBeritainRepository: IBeritainRepository {
    override fun getNewsSources(): Flow<Resources<List<SourceModel>>> {
        return flow {
            emit(Resources.Success(
                listOf(
                    SourceModel("","","",""),
                    SourceModel("","","",""),
                    SourceModel("","","","")
                )
            ))
        }
    }

    override fun getTopHeadlineByCategory(newsRequest: NewsRequest): Flow<Resources<List<ArticleModel>>> {
        return flow { emit(Resources.Error(401,"")) }
    }

    override fun getFavoriteArticle(): Flow<Resources<List<ArticleModel>>> {
        return flow { emit(Resources.Success(listOf())) }
    }

    override suspend fun addFavoriteArticle(article: ArticleEntity) {}

    override suspend fun deleteFavoriteArticle(article: ArticleEntity) {}
}