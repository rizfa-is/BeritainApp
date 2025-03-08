package com.issog.core.data.source.remote.network

import com.issog.core.data.source.remote.response.SourceResponse
import com.issog.core.data.source.remote.response.TopHeadlineResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getNewsSources(@Url url: String): Response<SourceResponse>

    @GET
    suspend fun getTopHeadlineByCategory(
        @Url url: String,
        @Query("category") category: String,
        @Query("sources") sources: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("q") search: String
    ): Response<TopHeadlineResponse>
}