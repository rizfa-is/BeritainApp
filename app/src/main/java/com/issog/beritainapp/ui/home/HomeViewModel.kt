package com.issog.beritainapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.issog.core.utils.UiState
import com.issog.core.data.Resources
import com.issog.core.domain.model.SourceModel
import com.issog.core.domain.usecase.BeritainUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(private val beritainUseCase: BeritainUseCase): ViewModel() {
    private val _sourceList: MutableLiveData<UiState<List<SourceModel>>> = MutableLiveData()
    val sourceList: LiveData<UiState<List<SourceModel>>>
        get() = _sourceList

    fun getSources() {
        _sourceList.value = UiState.Loading
        viewModelScope.launch {
            beritainUseCase.getNewsSources().collectLatest { result ->
                _sourceList.value = when(result) {
                    is Resources.Success -> UiState.Success(result.data)
                    is Resources.Error -> UiState.Error(result.code, result.message)
                    is Resources.NetworkError -> UiState.NetworkError
                }
            }
        }
    }
}