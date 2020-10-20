package com.boristul.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.boristul.database.dao.TagsDao
import com.boristul.entity.Tag

class TagsRepository(
    private val tagsDao: TagsDao
) {
    fun getAllLiveData(): LiveData<List<Tag>> = tagsDao.getAllLiveData().map { it.toList<Tag>() }
}
