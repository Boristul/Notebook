package com.boristul.entity

import android.os.Parcelable

interface TagWithNotes : Parcelable {
    val tag: Tag
    val notes: List<Note>
}