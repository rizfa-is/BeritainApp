package com.issog.core.utils

sealed class UiState<out R> {
    data class Success<R>(val data: R): UiState<R>()
    data class Error(val code: Int = -1, val message: String = ""): UiState<Nothing>()
    object NetworkError: UiState<Nothing>()
    object Loading: UiState<Nothing>()
}