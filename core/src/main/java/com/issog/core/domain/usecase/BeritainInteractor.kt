package com.issog.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.issog.core.data.Resources
import com.issog.core.data.source.remote.request.NewsRequest
import com.issog.core.domain.model.ArticleModel
import com.issog.core.domain.model.SourceModel
import com.issog.core.domain.paging.NewsPagingSource
import com.issog.core.domain.repository.IBeritainRepository
import com.issog.core.utils.DataMapper.mapArticleDomainToEntity
import com.issog.core.utils.UiState
import kotlinx.coroutines.flow.collectLatest

class BeritainInteractor(private val repository: IBeritainRepository): BeritainUseCase {
    override fun getNewsSources(): LiveData<UiState<List<SourceModel>>> {
        return liveData {
            emit(UiState.Loading)
            repository.getNewsSources().collectLatest { result ->
                emit(
                    when(result) {
                        is Resources.Success -> UiState.Success(result.data)
                        is Resources.Error -> UiState.Error(result.code, result.message)
                        is Resources.NetworkError -> UiState.NetworkError
                    }
                )
            }
        }
    }

    override fun getTopHeadlineByCategory(newsRequest: NewsRequest): LiveData<PagingData<ArticleModel>> {
        return Pager(
            config = PagingConfig(pageSize = 5, initialLoadSize = 10, prefetchDistance = 1, enablePlaceholders = false),
            pagingSourceFactory = { NewsPagingSource(repository, newsRequest) }
        ).liveData
    }

    override fun getFavoriteArticle(query: String): LiveData<UiState<List<ArticleModel>>> {
        return liveData {
            emit(UiState.Loading)
            repository.getFavoriteArticle().collectLatest { result ->
                emit(
                    when(result) {
                        is Resources.Error -> UiState.Error(result.code, result.message)
                        is Resources.NetworkError -> UiState.NetworkError
                        is Resources.Success -> {
                            val favorite = result.data
                            val newFavorite = favorite.toMutableList()
                            newFavorite.removeIf { !it.title.lowercase().contains(query.lowercase()) }

                            val emitData = if (query.isNotEmpty()) newFavorite else favorite
                            UiState.Success(emitData)
                        }
                    }
                )
            }
        }
    }

    override suspend fun addFavoriteArticle(article: ArticleModel) {
        repository.addFavoriteArticle(article.mapArticleDomainToEntity())
    }

    override suspend fun deleteFavoriteArticle(article: ArticleModel) {
        repository.deleteFavoriteArticle(article.mapArticleDomainToEntity())
    }
}