package com.boristul.notebook.ui.notes.noteinfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.boristul.entity.Note
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat

class NoteInfoFragmentViewModel(
    application: Application,
    val note: Note
) : AndroidViewModel(application) {
    private val dateTimePattern = DateTimeFormat.forPattern("dd.MM.yyyy (HH:mm)").withZone(DateTimeZone.getDefault())

    fun getDateTimeString() = note.creationTime.toString(dateTimePattern)
}
