package com.boristul.notebook.ui.notes.noteeditor

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.boristul.entity.Note
import com.boristul.repository.NotesRepository
import org.joda.time.DateTime
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class NoteEditorFragmentViewModel(
    application: Application,
    private val note: Note?
) : AndroidViewModel(application), DIAware {

    override val di: DI by di()
    private val notesRepository by instance<NotesRepository>()

    val title = MutableLiveData("")
    val description = MutableLiveData("")
    var isEdition: Boolean = false
        private set

    init {
        note?.let {
            isEdition = true
            title.value = it.title
            description.value = it.description
        }
    }

    suspend fun save() = notesRepository.apply {
        if (!isEdition) insert(requireNotNull(title.value), requireNotNull(description.value), DateTime.now())
        else update(requireNotNull(title.value), requireNotNull(description.value), DateTime.now(), requireNotNull(note).id)
    }
}
