package com.issog.core.data.source.local.room.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.issog.core.data.source.remote.response.TopHeadlineResponse.Source

@Entity(tableName = "article_table")
data class ArticleEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "publishedAt")
    var publishedAt: String? = null,
    @ColumnInfo(name = "author")
    var author: String? = null,
    @ColumnInfo(name = "urlToImage")
    var urlToImage: String? = null,
    @ColumnInfo(name = "description")
    var description: String? = null,
    @ColumnInfo(name = "source")
    var source: Source? = null,
    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "url")
    var url: String? = null,
    @ColumnInfo(name = "content")
    var content: String? = null,
    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false
)
