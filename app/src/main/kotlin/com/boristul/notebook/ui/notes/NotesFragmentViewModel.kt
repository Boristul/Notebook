package com.boristul.notebook.ui.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.boristul.repository.NotesRepository
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class NotesFragmentViewModel(application: Application) : AndroidViewModel(application), DIAware {
    override val di: DI by di()
    private val notesRepository by instance<NotesRepository>()

    val notes = notesRepository.getAll()
}
