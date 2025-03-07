package com.issog.core.data.source.remote.network

sealed class ApiResponse<out R> {
    data class Success<R>(val data: R): ApiResponse<R>()
    data class Error(val code: Int = -1, val message: String = ""): ApiResponse<Nothing>()
    data object NetworkError: ApiResponse<Nothing>()
}