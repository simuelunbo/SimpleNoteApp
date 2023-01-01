package com.simuelunbo.simplenoteapp.notes

import com.simuelunbo.simplenoteapp.domain.model.Note
import com.simuelunbo.simplenoteapp.domain.util.NoteOrder
import com.simuelunbo.simplenoteapp.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
