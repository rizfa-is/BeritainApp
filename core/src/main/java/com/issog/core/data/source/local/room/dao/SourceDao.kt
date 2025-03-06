package com.issog.core.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.issog.core.data.source.local.room.entites.SourceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceDao {

    @Query("SELECT * FROM source_table")
    fun getSources(): Flow<List<SourceEntity>>

    @Query("SELECT * FROM source_table WHERE favorite = 1")
    fun getFavoriteSource(): Flow<List<SourceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSource(sources: List<SourceEntity>)

    @Update
    fun updateFavoriteSource(sourceEntity: SourceEntity)
}