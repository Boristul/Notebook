package com.boristul.notebook.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boristul.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesFragmentViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    private val statePrivate = MutableStateFlow<NoteState>(NoteState.Started)
    val state = statePrivate.asStateFlow()

    val notes = notesRepository.getAll()
    fun delete(id: Long) = viewModelScope.launch {
        notesRepository.delete(id)
        statePrivate.value = NoteState.NoteDeleted(id)
    }
}
