package com.boristul.notebook.ui.notes.noteeditor

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boristul.entity.NoteWithTags
import com.boristul.entity.Tag
import com.boristul.repository.NotesRepository
import com.boristul.repository.TagsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class NoteEditorFragmentViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    tagsRepository: TagsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteWithTags: NoteWithTags? = savedStateHandle.get("note")

    val title = MutableLiveData("")
    val description = MutableLiveData("")
    var isEdition: Boolean = false
        private set

    private val selectedTags = noteWithTags?.tags?.toMutableList() ?: mutableListOf()
    val tags = tagsRepository.getAll()
    fun updateTagsList(tag: Tag, isSelected: Boolean) = selectedTags.run { if (isSelected) add(tag) else remove(tag) }

    init {
        noteWithTags?.let {
            isEdition = true
            title.value = it.note.title
            description.value = it.note.description
        }
    }

    private val statePrivate = MutableStateFlow<NoteEditorState>(NoteEditorState.Started)
    val state = statePrivate.asStateFlow()

    val isDataNotEmpty = MediatorLiveData<Boolean>().apply {
        val observer = Observer<Any?> {
            value = !title.value.isNullOrBlank() || !description.value.isNullOrBlank()
        }
        addSource(title, observer)
        addSource(description, observer)
    }

    fun isTagSelected(tag: Tag): Boolean = selectedTags.contains(tag)

    fun save() = viewModelScope.launch {
        notesRepository.apply {
            if (!isEdition) {
                insert(requireNotNull(title.value), requireNotNull(description.value), DateTime.now(), selectedTags)
            } else {
                update(
                    requireNotNull(title.value),
                    requireNotNull(description.value),
                    DateTime.now(),
                    requireNotNull(noteWithTags).note.id,
                    selectedTags
                )
            }
        }
        statePrivate.value = NoteEditorState.SaveCompleted
    }
}
