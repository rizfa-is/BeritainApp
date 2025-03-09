package com.issog.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.issog.core.data.Resources
import com.issog.core.data.source.remote.request.NewsRequest
import com.issog.core.domain.model.ArticleModel
import com.issog.core.domain.model.SourceModel
import com.issog.core.utils.UiState
import kotlinx.coroutines.flow.Flow

interface BeritainUseCase {
    fun getNewsSources(): LiveData<UiState<List<SourceModel>>>
    fun getTopHeadlineByCategory(newsRequest: NewsRequest): LiveData<PagingData<ArticleModel>>
    fun getFavoriteArticle(): Flow<Resources<List<ArticleModel>>>
    suspend fun addFavoriteArticle(article: ArticleModel)
    fun updateFavoriteArticle(article: ArticleModel)
}