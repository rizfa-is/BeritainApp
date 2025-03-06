package com.issog.core.data.source.local.room.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "source_table")
data class SourceEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "country")
    var country: String? = null,
    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "description")
    var description: String? = null,
    @ColumnInfo(name = "language")
    var language: String? = null,
    @ColumnInfo(name = "sourceId")
    var sourceId: String? = null,
    @ColumnInfo(name = "category")
    var category: String? = null,
    @ColumnInfo(name = "url")
    var url: String? = null,
    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false
)
