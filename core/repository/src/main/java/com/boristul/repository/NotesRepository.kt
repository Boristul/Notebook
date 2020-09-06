package com.boristul.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.boristul.database.dao.NotesDao
import com.boristul.database.entity.NoteEntity
import com.boristul.entity.Note
import org.joda.time.DateTime

class NotesRepository(
    private val notesDao: NotesDao
) {
    suspend fun insert(title: String, text: String, dateTime: DateTime) {
        notesDao.insert(NoteEntity(title, text, dateTime))
    }

    suspend fun update(title: String, text: String, dateTime: DateTime) {
        notesDao.update(NoteEntity(title, text, dateTime))
    }

    fun getAll(): LiveData<List<Note>> = notesDao.getAllLiveData().map()

    private fun LiveData<List<NoteEntity>>.map() = map { it.toList<Note>() }
}
