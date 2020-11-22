package com.boristul.entity

import android.os.Parcelable
import org.joda.time.LocalDate

interface TaskPoint : Parcelable {
    val task: String
    val date: LocalDate
    val isCompleted: Boolean
    val id: Long
}
