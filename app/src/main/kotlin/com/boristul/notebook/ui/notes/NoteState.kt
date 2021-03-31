package com.boristul.notebook.ui.notes

sealed class NoteState {
    object Started : NoteState()
    object NoteDeleted : NoteState()
}
