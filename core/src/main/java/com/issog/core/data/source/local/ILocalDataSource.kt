package com.issog.core.data.source.local

import com.issog.core.data.source.local.room.entites.ArticleEntity
import com.issog.core.data.source.local.room.entites.SourceEntity
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {
    /* Source */
    fun getSources(): Flow<List<SourceEntity>>
    fun getFavoriteSource(): Flow<List<SourceEntity>>
    suspend fun insertSource(sources: List<SourceEntity>)
    fun updateFavoriteSource(sourceEntity: SourceEntity)

    /* Article */
    fun getArticles(): Flow<List<ArticleEntity>>
    fun getFavoriteArticle(): Flow<List<ArticleEntity>>
    suspend fun insertArticles(articles: List<ArticleEntity>)
    fun updateFavoriteArticle(articleEntity: ArticleEntity)
}