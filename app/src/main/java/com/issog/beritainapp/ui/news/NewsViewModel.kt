package com.issog.beritainapp.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.issog.core.data.source.remote.request.NewsRequest
import com.issog.core.domain.model.ArticleModel
import com.issog.core.domain.usecase.BeritainUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsViewModel(private val beritainUseCase: BeritainUseCase): ViewModel() {
    fun getNews(
        category: String,
        source: String,
        query: String = ""
    ): LiveData<PagingData<ArticleModel>> {
        val request = NewsRequest(category = category, sourceId = source, search = query)
        return beritainUseCase.getTopHeadlineByCategory(request).cachedIn(viewModelScope)
    }
}