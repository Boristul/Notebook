package com.boristul.repository

import com.boristul.database.dao.NotesDao
import com.boristul.database.entity.NoteEntity
import com.boristul.database.entity.NoteWithTagsEntity
import com.boristul.database.entity.TagEntity
import com.boristul.entity.NoteWithTags
import com.boristul.entity.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.joda.time.DateTime
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val notesDao: NotesDao
) : NotesRepository {
    override suspend fun insert(title: String, text: String, dateTime: DateTime, tags: List<Tag>) {
        // TODO: Think about this constructions
        notesDao.insert(NoteWithTagsEntity(NoteEntity(title, text, dateTime), tags.map { it.toEntity() }))
    }

    override suspend fun update(title: String, text: String, dateTime: DateTime, id: Long, tags: List<Tag>) {
        // TODO: Think about this constructions
        notesDao.update(NoteWithTagsEntity(NoteEntity(title, text, dateTime, id), tags.map { it.toEntity() }))
    }

    override suspend fun delete(id: Long) = notesDao.delete(id)

    override fun getAll(): Flow<List<NoteWithTags>> = notesDao.getAllFlow().map()

    private fun Flow<List<NoteWithTagsEntity>>.map() = map { it.toList<NoteWithTags>() }
    private fun Tag.toEntity() = TagEntity(name, id)
}
