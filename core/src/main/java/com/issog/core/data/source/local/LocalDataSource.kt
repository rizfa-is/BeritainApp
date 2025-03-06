package com.issog.core.data.source.local

import com.issog.core.data.source.local.room.dao.ArticleDao
import com.issog.core.data.source.local.room.dao.SourceDao
import com.issog.core.data.source.local.room.entites.ArticleEntity
import com.issog.core.data.source.local.room.entites.SourceEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class LocalDataSource(
    private val articleDao: ArticleDao,
    private val sourceDao: SourceDao
): ILocalDataSource {
    override fun getSources(): Flow<List<SourceEntity>> {
        return sourceDao.getSources()
    }

    override fun getFavoriteSource(): Flow<List<SourceEntity>> {
        return sourceDao.getFavoriteSource()
            .flowOn(Dispatchers.Default)
    }

    override suspend fun insertSource(sources: List<SourceEntity>) {
        sourceDao.insertSource(sources)
    }

    override fun updateFavoriteSource(sourceEntity: SourceEntity) {
        sourceDao.updateFavoriteSource(sourceEntity)
    }

    override fun getArticles(): Flow<List<ArticleEntity>> {
        return articleDao.getArticles()
    }

    override fun getFavoriteArticle(): Flow<List<ArticleEntity>> {
        return articleDao.getFavoriteArticle()
            .flowOn(Dispatchers.Default)
    }

    override suspend fun insertArticles(articles: List<ArticleEntity>) {
        articleDao.insertArticles(articles)
    }

    override fun updateFavoriteArticle(articleEntity: ArticleEntity) {
        articleDao.updateFavoriteArticle(articleEntity)
    }
}