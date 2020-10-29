package com.boristul.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.boristul.database.entity.TagEntity
import com.boristul.database.entity.TagWithNotesEntity

@Dao
interface TagsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tag: TagEntity)

    @Query("SELECT * FROM tags")
    suspend fun getAll(): List<TagEntity>

    @Query("SELECT * FROM tags")
    fun getAllLiveData(): LiveData<List<TagEntity>>

    @Transaction
    @Query("SELECT * FROM tags")
    fun getAllTagsWithNotesLiveData(): LiveData<List<TagWithNotesEntity>>

    @Query("DELETE FROM tags WHERE _id = :id")
    suspend fun delete(id: Long)
}
