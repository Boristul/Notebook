package com.boristul.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.boristul.database.dao.TaskPointsDao
import com.boristul.database.entity.TaskPointEntity
import com.boristul.entity.TaskPoint
import org.joda.time.LocalDate

class TaskPointsRepository(
    private val taskPointsDao: TaskPointsDao
) {
    suspend fun insert(title: String, date: LocalDate) = taskPointsDao.insert(TaskPointEntity(title, date))

    suspend fun update(id: Long, isCompleted: Boolean) = taskPointsDao.update(id, isCompleted)
    suspend fun update(id: Long, title: String) = taskPointsDao.update(id, title)

    suspend fun update(taskPoint: TaskPoint) = taskPointsDao.update(taskPoint.toEntity())

    fun getTaskPoints(date: LocalDate): LiveData<List<TaskPoint>> = taskPointsDao.get(date).map()

    suspend fun delete(id: Long) = taskPointsDao.delete(id)

    private fun LiveData<List<TaskPointEntity>>.map() = map { it.toList<TaskPoint>() }
    private fun TaskPoint.toEntity() = TaskPointEntity(title, date, isCompleted, id)
}
