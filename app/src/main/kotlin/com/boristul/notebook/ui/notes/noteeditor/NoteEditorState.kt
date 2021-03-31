package com.boristul.notebook.ui.notes.noteeditor

sealed class NoteEditorState {
    object Started : NoteEditorState()
    object SaveCompleted : NoteEditorState()
    object Loading : NoteEditorState()
}
