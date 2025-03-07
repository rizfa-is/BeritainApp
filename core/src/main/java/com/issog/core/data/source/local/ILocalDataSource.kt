package com.issog.core.data.source.local

import com.issog.core.data.source.local.room.entites.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {
    fun getArticles(): Flow<List<ArticleEntity>>
    fun getFavoriteArticle(): Flow<List<ArticleEntity>>
    suspend fun insertArticles(articles: List<ArticleEntity>)
    fun updateFavoriteArticle(articleEntity: ArticleEntity)
}