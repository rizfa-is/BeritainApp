package com.issog.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.issog.core.data.source.remote.request.NewsRequest
import com.issog.core.domain.model.ArticleModel
import com.issog.core.domain.model.SourceModel
import com.issog.core.utils.UiState

interface BeritainUseCase {
    fun getNewsSources(): LiveData<UiState<List<SourceModel>>>
    fun getTopHeadlineByCategory(newsRequest: NewsRequest): LiveData<PagingData<ArticleModel>>
    fun getFavoriteArticle(query: String): LiveData<UiState<List<ArticleModel>>>
    suspend fun addFavoriteArticle(article: ArticleModel)
    suspend fun deleteFavoriteArticle(article: ArticleModel)
}