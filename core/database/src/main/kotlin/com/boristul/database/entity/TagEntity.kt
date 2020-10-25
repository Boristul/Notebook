package com.boristul.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.boristul.entity.Tag
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "tags"
)
@Parcelize
data class TagEntity(

    @ColumnInfo(name = "name")
    override val name: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    override val id: Long = 0L
) : Tag
