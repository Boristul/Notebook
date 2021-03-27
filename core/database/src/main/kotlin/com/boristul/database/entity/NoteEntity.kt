package com.boristul.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.boristul.entity.Note
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTime
import org.joda.time.DateTimeUtils.currentTimeMillis

@Entity(
    tableName = "notes"
)
@Parcelize
data class NoteEntity(

    @ColumnInfo(name = "title")
    override val title: String,

    @ColumnInfo(name = "description")
    override val description: String,

    @ColumnInfo(name = "creationTime")
    override val creationTime: DateTime,

    @PrimaryKey
    @ColumnInfo(name = "_id")
    override val id: Long = currentTimeMillis()

) : Note
