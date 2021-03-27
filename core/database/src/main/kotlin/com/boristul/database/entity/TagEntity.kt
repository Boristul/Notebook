package com.boristul.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.boristul.entity.Tag
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTimeUtils.currentTimeMillis

@Parcelize
@Entity(tableName = "tags")
data class TagEntity(

    @ColumnInfo(name = "name")
    override val name: String,

    @PrimaryKey
    @ColumnInfo(name = "_id")
    override val id: Long = currentTimeMillis()
) : Tag
