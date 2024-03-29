package com.boristul.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.boristul.database.entity.NoteEntity
import com.boristul.database.entity.NoteTagCrossRef
import com.boristul.database.entity.NoteWithTagsEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NotesDao {

    @Transaction
    open suspend fun insert(noteWithTagsEntity: NoteWithTagsEntity) {
        noteWithTagsEntity.run {
            insert(note)
            tags.forEach { tag ->
                insert(NoteTagCrossRef(note.id, tag.id))
            }
        }
    }

    @Transaction
    open suspend fun update(noteWithTagsEntity: NoteWithTagsEntity) {
        noteWithTagsEntity.run {
            update(note)
            deleteTagsForNote(note.id)
            tags.forEach { tag ->
                insert(NoteTagCrossRef(note.id, tag.id))
            }
        }
    }

    @Transaction
    @Query("SELECT * FROM notes ORDER BY _id DESC")
    abstract fun getAllFlow(): Flow<List<NoteWithTagsEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    protected abstract suspend fun insert(note: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insert(crossRef: NoteTagCrossRef)

    @Update(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun update(note: NoteEntity)

    @Query("DELETE FROM notes WHERE _id = :id")
    abstract suspend fun delete(id: Long)

    @Query("DELETE from note_tag_cross_ref WHERE note_id = :noteId")
    abstract suspend fun deleteTagsForNote(noteId: Long)
}
