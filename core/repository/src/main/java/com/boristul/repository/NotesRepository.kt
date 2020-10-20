package com.boristul.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.boristul.database.dao.NotesDao
import com.boristul.database.entity.NoteEntity
import com.boristul.database.entity.NoteWithTagsEntity
import com.boristul.database.entity.TagEntity
import com.boristul.entity.NoteWithTags
import com.boristul.entity.Tag
import org.joda.time.DateTime

class NotesRepository(
    private val notesDao: NotesDao
) {
    suspend fun insert(title: String, text: String, dateTime: DateTime, tags: List<Tag>) {
        notesDao.insert(
            NoteWithTagsEntity(
                NoteEntity(title, text, dateTime),
                tags.map { it.toEntity() }
            )
        )
    }

    suspend fun update(title: String, text: String, dateTime: DateTime, id: Long) {
        notesDao.update(NoteEntity(title, text, dateTime, id))
    }

    suspend fun delete(id: Long) = notesDao.delete(id)

    fun getAll(): LiveData<List<NoteWithTags>> = notesDao.getAllLiveData().map()

    private fun LiveData<List<NoteWithTagsEntity>>.map() = map { it.toList<NoteWithTags>() }
    private fun Tag.toEntity() = TagEntity(name, id)
}
