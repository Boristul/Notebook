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
abstract class TagDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(tag: TagEntity)

    @Query("SELECT * FROM tags")
    abstract suspend fun getAll(): List<TagEntity>

    @Query("SELECT * FROM tags")
    abstract fun getAllLiveData(): LiveData<List<TagEntity>>

    @Transaction
    @Query("SELECT * FROM tags")
    abstract fun getAllTagsWithNotesLiveData(): LiveData<List<TagWithNotesEntity>>
}
