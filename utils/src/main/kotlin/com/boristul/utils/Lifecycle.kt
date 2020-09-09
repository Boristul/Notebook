package com.boristul.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.distinctUntilChanged

fun <T> LiveData<T>.distinctUntilChanged(checker: (newValue: T) -> Boolean) = MediatorLiveData<T>().apply {
    addSource(this@distinctUntilChanged.distinctUntilChanged()) { if (!checker(it)) value = it }
}
