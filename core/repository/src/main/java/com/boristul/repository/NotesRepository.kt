package com.boristul.repository

import com.boristul.entity.NoteWithTags
import com.boristul.entity.Tag
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

interface NotesRepository {

    suspend fun insert(title: String, text: String, dateTime: DateTime, tags: List<Tag>)
    suspend fun update(title: String, text: String, dateTime: DateTime, id: Long, tags: List<Tag>)
    suspend fun delete(id: Long)
    fun getAll(): Flow<List<NoteWithTags>>
}
