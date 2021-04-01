package com.boristul.repository

import com.boristul.database.dao.TagsDao
import com.boristul.database.entity.TagEntity
import com.boristul.entity.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TagsRepository(private val tagsDao: TagsDao) {
    suspend fun insert(name: String) = tagsDao.insert(TagEntity(name))
    fun getAll(): Flow<List<Tag>> = tagsDao.getAllFlow().map { it.toList<Tag>() }

    suspend fun delete(id: Long) = tagsDao.delete(id)
}
