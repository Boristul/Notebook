package com.boristul.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.boristul.database.dao.TaskPointsDao
import com.boristul.database.entity.TaskPointEntity
import com.boristul.entity.TaskPoint
import org.joda.time.LocalDate

class TaskPointRepository(
    private val taskPointsDao: TaskPointsDao
) {
    suspend fun insert(title: String, date: LocalDate) = taskPointsDao.insert(TaskPointEntity(title, date))

    fun getTaskPoints(date: LocalDate): LiveData<List<TaskPoint>> = taskPointsDao.get(date).map()

    suspend fun delete(id: Long) = taskPointsDao.delete(id)

    private fun LiveData<List<TaskPointEntity>>.map() = map { it.toList<TaskPoint>() }
}
