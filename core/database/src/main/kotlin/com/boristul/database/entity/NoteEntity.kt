package com.boristul.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.boristul.entity.Note
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime

@Entity(
    tableName = "notes"
)
@Parcelize
data class NoteEntity(

    @ColumnInfo(name = "title")
    override val title: String,

    @ColumnInfo(name = "text")
    override val text: String,

    @ColumnInfo(name = "creationTime")
    override val creationTime: DateTime,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    override val id: Long = 0L

) : Note {
    init {
        require(title.isNotBlank()) { "Empty title" }
    }
}