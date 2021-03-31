package com.boristul.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.boristul.database.entity.TagEntity
import com.boristul.database.entity.TagWithNotesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tag: TagEntity)

    @Query("SELECT * FROM tags")
    suspend fun getAll(): List<TagEntity>

    @Query("SELECT * FROM tags")
    fun getAllFlow(): Flow<List<TagEntity>>

    @Transaction
    @Query("SELECT * FROM tags")
    fun getAllTagsWithNotesFlow(): Flow<List<TagWithNotesEntity>>

    @Query("DELETE FROM tags WHERE _id = :id")
    suspend fun delete(id: Long)
}
