package com.issog.beritainapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.issog.core.domain.model.ArticleModel
import com.issog.core.domain.usecase.BeritainUseCase
import com.issog.core.utils.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FavoriteViewModel(private val beritainUseCase: BeritainUseCase): ViewModel() {
    fun getFavoriteNews(query: String = ""): LiveData<UiState<List<ArticleModel>>> {
        return beritainUseCase.getFavoriteArticle(query)
    }
    fun deleteFavorite(articleModel: ArticleModel) = viewModelScope.launch {
        beritainUseCase.deleteFavoriteArticle(articleModel)
        delay(500)
        getFavoriteNews()
    }
}