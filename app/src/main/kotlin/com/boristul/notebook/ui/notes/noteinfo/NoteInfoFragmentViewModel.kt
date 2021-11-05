package com.boristul.notebook.ui.notes.noteinfo

import androidx.lifecycle.ViewModel
import com.boristul.entity.NoteWithTags
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat

class NoteInfoFragmentViewModel(
    val noteWithTags: NoteWithTags
) : ViewModel() {
    private val dateTimePattern = DateTimeFormat.forPattern("dd.MM.yyyy (HH:mm)").withZone(DateTimeZone.getDefault())

    fun getDateTimeString(): String = noteWithTags.note.creationTime.toString(dateTimePattern)

    fun getNoteForShare() = "${noteWithTags.note.title}\n${noteWithTags.note.description}\n${getDateTimeString()}"
}
