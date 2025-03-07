package com.issog.core.data

import com.issog.core.data.source.local.LocalDataSource
import com.issog.core.data.source.local.room.entites.ArticleEntity
import com.issog.core.data.source.remote.RemoteDataSource
import com.issog.core.data.source.remote.RemoteSafeApiCall
import com.issog.core.data.source.remote.network.ApiResponse
import com.issog.core.domain.model.ArticleModel
import com.issog.core.domain.model.SourceModel
import com.issog.core.domain.repository.IBeritainRepository
import com.issog.core.utils.DataMapper.mapArticleEntityToModel
import com.issog.core.utils.DataMapper.mapArticleResponseToModel
import com.issog.core.utils.DataMapper.mapSourceResponseToModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import java.io.IOException

class BeritainRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): IBeritainRepository, RemoteSafeApiCall() {
    override fun getNewsSources(): Flow<Resources<List<SourceModel>>> {
        return flow {
            try {
                emit(Resources.Loading())
                remoteDataSource.getNewsSources().collectLatest { api ->
                    when(api) {
                        is ApiResponse.Success -> emit(Resources.Success(api.data.sources.mapSourceResponseToModel()))
                        is ApiResponse.Error -> emit(Resources.Error(api.code, api.message))
                        else -> emit(Resources.NetworkError())
                    }
                }
            } catch (e: IOException) {
                emit(Resources.NetworkError())
            } catch (e: Exception) {
                emit(Resources.Error(0, e.message.toString()))
            }
        }
    }

    override fun getTopHeadlineByCategory(category: String): Flow<Resources<List<ArticleModel>>> {
        return flow {
            try {
                emit(Resources.Loading())
                remoteDataSource.getTopHeadlineByCategory(category).collectLatest { api ->
                    when(api) {
                        is ApiResponse.Success -> emit(Resources.Success(api.data.articles.mapArticleResponseToModel()))
                        is ApiResponse.Error -> emit(Resources.Error(api.code, api.message))
                        else -> emit(Resources.NetworkError())
                    }
                }
            } catch (e: IOException) {
                emit(Resources.NetworkError())
            } catch (e: Exception) {
                emit(Resources.Error(0, e.message.toString()))
            }
        }
    }

    override fun getFavoriteArticle(): Flow<Resources<List<ArticleModel>>> {
        return flow {
            try {
                emit(Resources.Loading())
                localDataSource.getFavoriteArticle().collectLatest { articles ->
                    emit(Resources.Success(articles.mapArticleEntityToModel()))
                }
            } catch (e: Exception) {
                emit(Resources.Error(0, e.message.toString()))
            }
        }
    }

    override suspend fun addFavoriteArticle(article: ArticleEntity) {
        localDataSource.insertFavoriteArticles(article)
    }

    override fun updateFavoriteArticle(article: ArticleEntity,) {
        localDataSource.updateFavoriteArticle(article)
    }
}