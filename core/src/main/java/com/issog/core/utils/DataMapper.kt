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
                indexOf(article),
                article.urlToImage.orEmpty(),
                article.description.orEmpty(),
                article.source?.name.orEmpty(),
                article.title.orEmpty(),
                article.url.orEmpty(),
                false
            )
        } ?: emptyList()

    fun List<ArticleEntity>.mapArticleEntityToModel(): List<ArticleModel> =
        this.map { article ->
            ArticleModel(
                article.id,
                article.urlToImage.orEmpty(),
                article.description.orEmpty(),
                article.source.orEmpty(),
                article.title.orEmpty(),
                article.url.orEmpty(),
                false
            )
        }

    fun ArticleModel.mapArticleDomainToEntity(): ArticleEntity =
        ArticleEntity(
            id,
            urlToImage,
            description,
            source,
            title,
            url,
            favorite
        )
}