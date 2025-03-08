package com.issog.core.data.source.remote

import com.issog.core.data.source.remote.network.ApiResponse
import com.issog.core.data.source.remote.request.NewsRequest
import com.issog.core.data.source.remote.response.SourceResponse
import com.issog.core.data.source.remote.response.TopHeadlineResponse
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {
    suspend fun getNewsSources(): Flow<ApiResponse<SourceResponse>>
    suspend fun getTopHeadlineByCategory(newsRequest: NewsRequest): Flow<ApiResponse<TopHeadlineResponse>>
}