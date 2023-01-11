package com.simuelunbo.simplenoteapp.domain.usecase

import com.simuelunbo.simplenoteapp.domain.model.Note
import com.simuelunbo.simplenoteapp.domain.repository.NoteRepository
import com.simuelunbo.simplenoteapp.domain.util.NoteOrder
import com.simuelunbo.simplenoteapp.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            orderTypeNote(noteOrder, notes)
        }
    }
    private fun orderTypeNote(noteOrder: NoteOrder, notes: List<Note>): List<Note> {
        return when (noteOrder.orderType) {
            is OrderType.Ascending -> sortAscendingNoteOrderDetail(noteOrder,notes)
            is OrderType.Descending -> sortDescendingNoteOrderDetail(noteOrder, notes)
        }
    }

    private fun sortAscendingNoteOrderDetail(noteOrder: NoteOrder, notes: List<Note>): List<Note> {
        return when(noteOrder) {
            is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
            is NoteOrder.Date -> notes.sortedBy { it.timestamp }
            is NoteOrder.Color -> notes.sortedBy { it.color }
        }
    }

    private fun sortDescendingNoteOrderDetail(noteOrder: NoteOrder, notes: List<Note>): List<Note> {
        return when(noteOrder) {
            is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
            is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
            is NoteOrder.Color -> notes.sortedByDescending { it.color }
        }
    }
}