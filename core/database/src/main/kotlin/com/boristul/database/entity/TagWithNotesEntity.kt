package com.boristul.database.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.boristul.entity.TagWithNotes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TagWithNotesEntity(
    @Embedded override val tag: TagEntity,
    @Relation(
        parentColumn = "_id",
        entityColumn = "note_id",
        entity = NoteTagCrossRef::class,
        projection = ["tag_id"]
    )
    override val notes: List<NoteEntity>
) : TagWithNotes
