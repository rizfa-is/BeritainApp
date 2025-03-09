package com.issog.core.source

import com.issog.core.data.source.local.ILocalDataSource
import com.issog.core.data.source.local.room.entites.ArticleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalDataSource: ILocalDataSource {
    override fun getFavoriteArticle(): Flow<List<ArticleEntity>> {
        return flow {
            emit(listOf(
                ArticleEntity(0),
                ArticleEntity(1),
                ArticleEntity(2)
            ))
        }
    }

    override suspend fun insertFavoriteArticles(article: ArticleEntity) {}

    override fun deleteFavoriteArticle(articleEntity: ArticleEntity) {}
}