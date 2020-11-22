package com.boristul.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.boristul.database.entity.TaskPointEntity
import java.time.LocalDate

@Dao
interface TaskPointsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(taskPoint: TaskPointEntity)

    @Query("SELECT * FROM task_points WHERE date = :date")
    fun get(date: LocalDate): LiveData<List<TaskPointEntity>>
}
