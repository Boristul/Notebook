package com.boristul.notebook.ui.notes

sealed class NoteState {
    object Started : NoteState()
    data class NoteDeleted(val noteId: Long) : NoteState()
}
