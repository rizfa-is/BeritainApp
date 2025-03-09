package com.issog.core.source

import com.issog.core.data.source.remote.IRemoteDataSource
import com.issog.core.data.source.remote.network.ApiResponse
import com.issog.core.data.source.remote.request.NewsRequest
import com.issog.core.data.source.remote.response.SourceResponse
import com.issog.core.data.source.remote.response.TopHeadlineResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRemoteDataSource: IRemoteDataSource {
    override suspend fun getNewsSources(): Flow<ApiResponse<SourceResponse>> {
        return flow {
            emit(ApiResponse.Success(
                SourceResponse(
                    status = "ok",
                    sources = listOf(
                        SourceResponse.SourcesItem(
                            name = "Weather is good!"
                        )
                    )
                )
            ))
        }
    }

    override suspend fun getTopHeadlineByCategory(newsRequest: NewsRequest): Flow<ApiResponse<TopHeadlineResponse>> {
        return flow {
            emit(ApiResponse.Success(
                TopHeadlineResponse(
                    status = "ok",
                    totalResults = 5,
                    articles = listOf(
                        TopHeadlineResponse.ArticlesItem(
                            source = TopHeadlineResponse.Source(
                                "ABC News"
                            ),
                            title = "Good News Everybody"
                        )
                    )
                )
            ))
        }
    }
}