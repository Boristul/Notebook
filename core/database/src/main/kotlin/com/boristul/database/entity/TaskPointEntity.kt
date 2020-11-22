package com.boristul.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.boristul.entity.TaskPoint
import kotlinx.android.parcel.Parcelize
import org.joda.time.LocalDate

@Parcelize
@Entity(tableName = "task_points")
data class TaskPointEntity(
    @ColumnInfo(name = "task")
    override val task: String,

    @ColumnInfo(name = "date")
    override val date: LocalDate,

    @ColumnInfo(name = "is_completed")
    override val isCompleted: Boolean,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    override val id: Long = 0
) : TaskPoint
