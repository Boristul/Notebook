package com.boristul.repository

import com.boristul.entity.TaskPoint
import kotlinx.coroutines.flow.Flow
import org.joda.time.LocalDate

interface TaskPointsRepository {

    suspend fun insert(title: String, date: LocalDate)

    suspend fun update(id: Long, isCompleted: Boolean)
    suspend fun update(id: Long, title: String)
    suspend fun update(taskPoint: TaskPoint)

    fun getTaskPoints(date: LocalDate): Flow<List<TaskPoint>>
    suspend fun delete(id: Long)
}
