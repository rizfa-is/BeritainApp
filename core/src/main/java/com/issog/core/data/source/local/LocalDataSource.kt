package com.issog.core.data.source.local

import com.issog.core.data.source.local.room.dao.ArticleDao
import com.issog.core.data.source.local.room.entites.ArticleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class LocalDataSource(private val articleDao: ArticleDao): ILocalDataSource {
    override fun getFavoriteArticle(): Flow<List<ArticleEntity>> {
        return articleDao.getFavoriteArticle()
            .flowOn(Dispatchers.Default)
    }

    override suspend fun insertFavoriteArticles(article: ArticleEntity) {
        articleDao.insertArticle(article)
    }

    override suspend fun deleteFavoriteArticle(articleEntity: ArticleEntity) {
        articleDao.deleteFavoriteArticle(articleEntity)
    }
}