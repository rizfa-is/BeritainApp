package com.issog.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleModel(
    val urlToImage: String,
    val content: String,
    val author: String,
    val title: String,
    val url: String,
    var favorite: Boolean
): Parcelable
