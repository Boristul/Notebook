package com.boristul.notebook.ui.notes.noteinfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.boristul.entity.NoteWithTags
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat

class NoteInfoFragmentViewModel(
    application: Application,
    val noteWithTags: NoteWithTags
) : AndroidViewModel(application) {
    private val dateTimePattern = DateTimeFormat.forPattern("dd.MM.yyyy (HH:mm)").withZone(DateTimeZone.getDefault())

    fun getDateTimeString(): String = noteWithTags.note.creationTime.toString(dateTimePattern)

    fun getNoteForShare() = "${noteWithTags.note.title}\n${noteWithTags.note.description}\n${getDateTimeString()}"
}
