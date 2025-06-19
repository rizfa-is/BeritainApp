package com.issog.core.data

import com.issog.core.data.source.local.ILocalDataSource
import com.issog.core.data.source.local.room.entites.ArticleEntity
import com.issog.core.data.source.remote.IRemoteDataSource
import com.issog.core.data.source.remote.RemoteSafeApiCall
import com.issog.core.data.source.remote.network.ApiResponse
import com.issog.core.data.source.remote.request.NewsRequest
import com.issog.core.data.source.remote.response.TopHeadlineResponse
import com.issog.core.domain.model.ArticleModel
import com.issog.core.domain.model.SourceModel
import com.issog.core.domain.repository.IBeritainRepository
import com.issog.core.utils.DataMapper.mapArticleEntityToModel
import com.issog.core.utils.DataMapper.mapArticleResponseToModel
import com.issog.core.utils.DataMapper.mapSourceResponseToModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.IOException

class BeritainRepository(
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocalDataSource
): IBeritainRepository, RemoteSafeApiCall() {
    override fun getNewsSources(): Flow<Resources<List<SourceModel>>> {
        return channelFlow {
            try {
                remoteDataSource.getNewsSources().collectLatest { api ->
                    when(api) {
                        is ApiResponse.Success -> trySend(Resources.Success(api.data.sources.mapSourceResponseToModel()))
                        is ApiResponse.Error -> trySend(Resources.Error(api.code, api.message))
                        else -> trySend(Resources.NetworkError())
                    }
                }
            } catch (_: IOException) {
                trySend(Resources.NetworkError())
            } catch (e: Exception) {
                trySend(Resources.Error(0, e.message.toString()))
            }
        }
    }

    override fun getTopHeadlineByCategory(newsRequest: NewsRequest): Flow<Resources<List<ArticleModel>>> {
        return channelFlow {
            try {
                remoteDataSource.getTopHeadlineByCategory(newsRequest).collectLatest { api ->
                    when(api) {
                        is ApiResponse.Error -> trySend(Resources.Error(api.code, api.message))
                        is ApiResponse.Success -> {
                            val articles = api.data.articles.mapFilterArticleData()
                            trySend(Resources.Success(articles))
                        }
                        else -> trySend(Resources.NetworkError())
                    }
                }
            } catch (_: IOException) {
                trySend(Resources.NetworkError())
            } catch (e: Exception) {
                trySend(Resources.Error(0, e.message.toString()))
            }
        }
    }

    override fun getFavoriteArticle(): Flow<Resources<List<ArticleModel>>> {
        return channelFlow {
            try {
                localDataSource.getFavoriteArticle().collectLatest { articles ->
                    trySend(Resources.Success(articles.mapArticleEntityToModel()))
                }
            } catch (e: Exception) {
                trySend(Resources.Error(0, e.message.toString()))
            }
        }
    }

    override suspend fun addFavoriteArticle(article: ArticleEntity) {
        localDataSource.insertFavoriteArticles(article)
    }

    override suspend fun deleteFavoriteArticle(article: ArticleEntity) {
        localDataSource.deleteFavoriteArticle(article)
    }

    private suspend fun List<TopHeadlineResponse.ArticlesItem>?.mapFilterArticleData(): List<ArticleModel> {
        val favoriteNews = localDataSource.getFavoriteArticle().first().mapArticleEntityToModel()
        val articleMapper = this.mapArticleResponseToModel().toMutableList()

        withContext(Dispatchers.Default) {
            favoriteNews.forEach { fav ->
                articleMapper.forEachIndexed { index, articleModel ->
                    if (fav.title == articleModel.title) {
                        articleMapper[index].favorite = true
                    }
                }
            }
        }

        return articleMapper
    }
}