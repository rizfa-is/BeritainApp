package com.issog.core.data.source.remote

import com.issog.core.data.source.remote.network.ApiResponse
import com.issog.core.utils.notNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import retrofit2.Response

abstract class RemoteSafeApiCall {
    fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Flow<ApiResponse<T>> {
        return flow {
            try {
                val response = apiCall()
                if (response.isSuccessful && response.body().notNull()) {
                    emit(ApiResponse.Success(response.body()!!))
                } else {
                    emit(ApiResponse.Error(response.code(), response.message()))
                }
            } catch (e: IOException) {
                emit(ApiResponse.NetworkError)
            } catch (e: Exception) {
                emit(ApiResponse.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}