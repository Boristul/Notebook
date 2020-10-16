package com.boristul.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "note_tag_cross_ref",
    primaryKeys = ["note_id", "tag_id"]
)
data class NoteTagCrossRef(
    @ColumnInfo(name = "note_id")
    val noteId: Long,

    @ColumnInfo(name = "tag_id")
    val tagId: Long,
)
