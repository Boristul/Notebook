package com.boristul.repository

import com.boristul.database.dao.TagsDao
import com.boristul.database.entity.TagEntity
import com.boristul.entity.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TagsRepositoryImpl @Inject constructor(
    private val tagsDao: TagsDao
) : TagsRepository {
    override suspend fun insert(name: String) = tagsDao.insert(TagEntity(name))
    override fun getAll(): Flow<List<Tag>> = tagsDao.getAllFlow().map { it.toList<Tag>() }

    override suspend fun delete(id: Long) = tagsDao.delete(id)
}
