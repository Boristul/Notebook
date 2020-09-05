package com.boristul.entity

import android.os.Parcelable
import org.joda.time.DateTime

interface Note : Parcelable {
    val title: String
    val text: String
    val creationTime: DateTime
    val id: Long
}
