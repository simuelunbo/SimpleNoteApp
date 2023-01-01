package com.simuelunbo.simplenoteapp.notes

import com.simuelunbo.simplenoteapp.domain.model.Note
import com.simuelunbo.simplenoteapp.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent()
    object RestoreNote : NotesEvent()
    object ToggleOrderSection : NotesEvent()
}
