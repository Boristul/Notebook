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
abstract class TagsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(tag: TagEntity)

    @Query("SELECT * FROM tags")
    abstract suspend fun getAll(): List<TagEntity>

    @Query("SELECT * FROM tags")
    abstract fun getAllLiveData(): LiveData<List<TagEntity>>

    @Transaction
    @Query("SELECT * FROM tags")
    abstract fun getAllTagsWithNotesLiveData(): LiveData<List<TagWithNotesEntity>>

    @Query("DELETE FROM tags WHERE _id = :id")
    abstract suspend fun delete(id: Long)

    @Query("SELECT * FROM tags INNER JOIN note_tag_cross_ref ON tags._id = tag_id AND :noteId = note_id")
    abstract suspend fun getTags(noteId: Long): List<TagEntity>
}
