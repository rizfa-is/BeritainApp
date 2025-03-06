package com.issog.core.data.source.remote.network

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getNewsSources(@Url url: String)

    @GET
    suspend fun getTopHeadlineByCategory(
        @Url url: String,
        @Query("category") category: String
    )
}