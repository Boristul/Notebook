package com.boristul.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.boristul.database.dao.TagsDao
import com.boristul.database.entity.TagEntity
import com.boristul.entity.Tag

class TagsRepository(
    private val tagsDao: TagsDao
) {
    suspend fun insert(name: String) = tagsDao.insert(TagEntity(name))
    fun getAllLiveData(): LiveData<List<Tag>> = tagsDao.getAllLiveData().map { it.toList<Tag>() }

    suspend fun delete(id: Long) = tagsDao.delete(id)
}
