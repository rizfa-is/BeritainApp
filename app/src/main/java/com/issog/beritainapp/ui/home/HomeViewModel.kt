package com.issog.beritainapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.issog.core.domain.model.SourceModel
import com.issog.core.domain.usecase.BeritainUseCase
import com.issog.core.utils.UiState

class HomeViewModel(private val beritainUseCase: BeritainUseCase) : ViewModel() {
    val sourceList: LiveData<UiState<List<SourceModel>>>
        get() = beritainUseCase.getNewsSources()
}
