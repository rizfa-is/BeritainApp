package com.issog.core.data

sealed class Resources<T> {
    data class Success<T>(val data: T): Resources<T>()
    data class Error<T>(val code: Int, val message: String): Resources<T>()
    data class Loading<T>(val data: T? = null): Resources<T>()
    data class NetworkError<T>(val data: T? = null): Resources<T>()
}