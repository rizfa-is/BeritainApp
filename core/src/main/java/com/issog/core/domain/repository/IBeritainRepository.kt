package com.issog.core.domain.repository

import com.issog.core.data.Resources
import com.issog.core.data.source.local.room.entites.ArticleEntity
import com.issog.core.domain.model.ArticleModel
import com.issog.core.domain.model.SourceModel
import kotlinx.coroutines.flow.Flow

interface IBeritainRepository {
    fun getNewsSources(): Flow<Resources<List<SourceModel>>>
    fun getTopHeadlineByCategory(category: String): Flow<Resources<List<ArticleModel>>>
    fun getFavoriteArticle(): Flow<Resources<List<ArticleModel>>>
    suspend fun addFavoriteArticle(article: ArticleEntity)
    fun updateFavoriteArticle(article: ArticleEntity)
}