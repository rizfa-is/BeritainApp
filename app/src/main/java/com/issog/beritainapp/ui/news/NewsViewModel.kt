package com.issog.beritainapp.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.issog.core.data.source.remote.request.NewsRequest
import com.issog.core.domain.model.ArticleModel
import com.issog.core.domain.paging.NewsPagingSource
import com.issog.core.domain.usecase.BeritainUseCase
import com.issog.core.utils.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsViewModel(private val beritainUseCase: BeritainUseCase): ViewModel() {
    private val _newsList: MutableStateFlow<UiState<PagingData<ArticleModel>>> = MutableStateFlow(UiState.Loading)
    val newsList: StateFlow<UiState<PagingData<ArticleModel>>>
        get() = _newsList
    private var searchJob: Job? = null

    fun getNews(category: String, source: String, query: String = "") {
        _newsList.value = UiState.Loading

        searchJob?.cancel()
        searchJob = null
        searchJob = viewModelScope.launch {
            delay(1000)
            val request = NewsRequest(category = category, sourceId = source, search = query)
            Pager(
                config = PagingConfig(pageSize = 5, initialLoadSize = 10, prefetchDistance = 1, enablePlaceholders = false),
                pagingSourceFactory = { NewsPagingSource(beritainUseCase, request) }
            ).flow
                .cachedIn(viewModelScope)
                .collectLatest { _newsList.value = UiState.Success(it) }
        }
    }
}