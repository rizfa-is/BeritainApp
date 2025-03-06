package com.issog.core.data.source.remote

import com.issog.core.data.source.remote.network.ApiResponse
import com.issog.core.data.source.remote.network.ApiService
import com.issog.core.data.source.remote.response.SourceResponse
import com.issog.core.data.source.remote.response.TopHeadlineResponse
import com.issog.core.utils.security.BeritainNativeLibs
import kotlinx.coroutines.flow.Flow

class RemoteDataSource(private val apiService: ApiService): IRemoteDataSource, RemoteSafeApiCall() {
    override suspend fun getNewsSources(): Flow<ApiResponse<SourceResponse>> {
        return safeApiCall {
            apiService.getNewsSources(BeritainNativeLibs.getNewsSourceUrl())
        }
    }

    override suspend fun getTopHeadlineByCategory(category: String): Flow<ApiResponse<TopHeadlineResponse>> {
        return safeApiCall {
            apiService.getTopHeadlineByCategory(
                BeritainNativeLibs.getTopHeadlineByCategoryUrl(),
                category
            )
        }
    }
}