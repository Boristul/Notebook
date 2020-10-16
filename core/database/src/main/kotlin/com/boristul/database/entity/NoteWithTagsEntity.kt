package com.boristul.database.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.boristul.entity.NoteWithTags

data class NoteWithTagsEntity(
    @Embedded override val note: NoteEntity,
    @Relation(
        parentColumn = "note_id",
        entityColumn = "tag_id",
        associateBy = Junction(NoteTagCrossRef::class)
    )
    override val tags: List<TagEntity>
) : NoteWithTags
