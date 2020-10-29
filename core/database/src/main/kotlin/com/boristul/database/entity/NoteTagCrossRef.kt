package com.boristul.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "note_tag_cross_ref",
    primaryKeys = ["note_id", "tag_id"],
    foreignKeys = [
        ForeignKey(
            entity = TagEntity::class,
            parentColumns = ["_id"],
            childColumns = ["tag_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = NoteEntity::class,
            parentColumns = ["_id"],
            childColumns = ["note_id"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [Index("tag_id"), Index("note_id")]
)
data class NoteTagCrossRef(
    @ColumnInfo(name = "note_id")
    val noteId: Long,

    @ColumnInfo(name = "tag_id")
    val tagId: Long,
)
