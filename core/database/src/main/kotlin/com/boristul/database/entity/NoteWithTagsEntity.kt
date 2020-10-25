package com.boristul.database.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.boristul.entity.NoteWithTags
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NoteWithTagsEntity(
    @Embedded override val note: NoteEntity,
    @Relation(
        parentColumn = "_id",
        entityColumn = "tag_id",
        entity = NoteTagCrossRef::class,
        projection = ["note_id"]
    )
    override val tags: List<TagEntity>
) : NoteWithTags
