package com.issog.core.data.source.remote

import com.issog.core.data.source.remote.network.ApiResponse
import com.issog.core.data.source.remote.response.SourceResponse
import com.issog.core.data.source.remote.response.TopHeadlineResponse
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {
    suspend fun getNewsSources(): Flow<ApiResponse<SourceResponse>>
    suspend fun getTopHeadlineByCategory(category: String): Flow<ApiResponse<TopHeadlineResponse>>
}