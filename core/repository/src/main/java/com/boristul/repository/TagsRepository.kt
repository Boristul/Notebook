package com.boristul.repository

import com.boristul.entity.Tag
import kotlinx.coroutines.flow.Flow

interface TagsRepository {

    suspend fun insert(name: String)
    fun getAll(): Flow<List<Tag>>
    suspend fun delete(id: Long)
}
