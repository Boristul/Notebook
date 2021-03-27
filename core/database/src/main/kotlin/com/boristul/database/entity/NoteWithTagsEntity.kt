package com.boristul.database.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.boristul.entity.NoteWithTags
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteWithTagsEntity(
    @Embedded override val note: NoteEntity,
    @Relation(
        parentColumn = "_id",
        entityColumn = "_id",
        entity = TagEntity::class,
        associateBy = Junction(
            value = NoteTagCrossRef::class,
            parentColumn = "note_id",
            entityColumn = "tag_id"
        )
    )
    override val tags: List<TagEntity>
) : NoteWithTags
