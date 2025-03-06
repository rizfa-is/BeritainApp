package com.issog.core.data.source.local.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.issog.core.data.source.local.room.dao.ArticleDao
import com.issog.core.data.source.local.room.dao.SourceDao
import com.issog.core.data.source.local.room.entites.ArticleEntity
import com.issog.core.data.source.local.room.entites.SourceEntity

@Database(
    entities = [
        SourceEntity::class,
        ArticleEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BeritainDatabase: RoomDatabase() {
    abstract fun sourceDao(): SourceDao
    abstract fun articleDao(): ArticleDao
}