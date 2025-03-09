package com.issog.core.utils

import com.issog.core.data.source.local.room.entites.ArticleEntity
import com.issog.core.data.source.remote.response.SourceResponse
import com.issog.core.data.source.remote.response.TopHeadlineResponse
import com.issog.core.domain.model.ArticleModel
import com.issog.core.domain.model.SourceModel

object DataMapper {
    fun List<SourceResponse.SourcesItem>?.mapSourceResponseToModel(): List<SourceModel> =
        this?.map { source ->
            SourceModel(
                source.name.orEmpty(),
                source.id.orEmpty(),
                source.description.orEmpty(),
                source.url.orEmpty()
            )
        } ?: emptyList()

    fun List<TopHeadlineResponse.ArticlesItem>?.mapArticleResponseToModel(): List<ArticleModel> =
        this?.map { article ->
            ArticleModel(
                article.urlToImage.orEmpty(),
                article.content.orEmpty(),
                article.author.orEmpty(),
                article.title.orEmpty(),
                article.url.orEmpty(),
                false
            )
        } ?: emptyList()

    fun List<ArticleEntity>.mapArticleEntityToModel(): List<ArticleModel> =
        this.map { article ->
            ArticleModel(
                article.urlToImage.orEmpty(),
                article.content.orEmpty(),
                article.author.orEmpty(),
                article.title.orEmpty(),
                article.url.orEmpty(),
                true
            )
        }

    fun ArticleModel.mapArticleDomainToEntity(): ArticleEntity =
        ArticleEntity(
            urlToImage = urlToImage,
            content = content,
            author = author,
            title = title,
            url = url
        )
}