package com.boristul.notebook.ui.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.boristul.repository.NotesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class NotesFragmentViewModel(application: Application) : AndroidViewModel(application), DIAware {
    override val di: DI by di()
    private val notesRepository by instance<NotesRepository>()

    private val statePrivate = MutableStateFlow<NoteState>(NoteState.Started)
    val state = statePrivate.asStateFlow()

    val notes = notesRepository.getAll()
    fun delete(id: Long) = viewModelScope.launch {
        notesRepository.delete(id)
        statePrivate.value = NoteState.NoteDeleted(id)
    }
}
