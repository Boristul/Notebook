package com.boristul.entity

import android.os.Parcelable

interface NoteWithTags : Parcelable {
    val note: Note
    val tags: List<Tag>
}
