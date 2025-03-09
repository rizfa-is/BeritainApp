package com.issog.core.data.source.local

import com.issog.core.data.source.local.room.entites.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {
    fun getFavoriteArticle(): Flow<List<ArticleEntity>>
    suspend fun insertFavoriteArticles(article: ArticleEntity)
    suspend fun deleteFavoriteArticle(articleEntity: ArticleEntity)
}