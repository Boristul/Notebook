package com.boristul.database.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.boristul.entity.TagWithNotes

data class TagWithNotesEntity(
    @Embedded override val tag: TagEntity,
    @Relation(
        parentColumn = "tag_id",
        entityColumn = "note_id",
        associateBy = Junction(NoteTagCrossRef::class)
    )
    override val notes: List<NoteEntity>
) : TagWithNotes