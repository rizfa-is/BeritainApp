package com.issog.core.domain.model

data class ArticleModel(
    val id: Int,
    val urlToImage: String,
    val content: String,
    val author: String,
    val title: String,
    val url: String,
    var favorite: Boolean
)
