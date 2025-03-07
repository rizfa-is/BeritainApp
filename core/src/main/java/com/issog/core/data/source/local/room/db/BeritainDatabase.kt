package com.issog.core.data.source.local.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.issog.core.data.source.local.room.dao.ArticleDao
import com.issog.core.data.source.local.room.entites.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BeritainDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}