package com.issog.core.domain.usecase

import com.issog.core.data.Resources
import com.issog.core.domain.model.ArticleModel
import com.issog.core.domain.model.SourceModel
import kotlinx.coroutines.flow.Flow

interface BeritainUseCase {
    fun getNewsSources(): Flow<Resources<List<SourceModel>>>
    fun getTopHeadlineByCategory(beritainCategory: BeritainCategory): Flow<Resources<List<ArticleModel>>>
    fun getFavoriteArticle(): Flow<Resources<List<ArticleModel>>>
    suspend fun addFavoriteArticle(article: ArticleModel)
    fun updateFavoriteArticle(article: ArticleModel)
}