package com.boristul.entity

import android.os.Parcelable

interface Tag : Parcelable {
    val name: String
    val id: Long
}
