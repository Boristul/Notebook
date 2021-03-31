package com.boristul.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <T> LiveData<T>.distinctUntilChanged(checker: (newValue: T) -> Boolean) = MediatorLiveData<T>().apply {
    addSource(this@distinctUntilChanged.distinctUntilChanged()) { if (!checker(it)) value = it }
}

fun <T> Flow<T>.collectOnStarted(scope: CoroutineScope, lifecycle: Lifecycle, body: (value: T) -> Unit) = scope.launch {
    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) { collect { body(it) } }
}
