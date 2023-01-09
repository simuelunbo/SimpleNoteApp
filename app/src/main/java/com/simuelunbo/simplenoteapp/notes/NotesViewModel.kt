package com.simuelunbo.simplenoteapp.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simuelunbo.simplenoteapp.domain.model.Note
import com.simuelunbo.simplenoteapp.domain.usecase.NoteUseCases
import com.simuelunbo.simplenoteapp.domain.util.NoteOrder
import com.simuelunbo.simplenoteapp.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _noteState = mutableStateOf<NotesState>(NotesState())
    val noteState: State<NotesState> = _noteState

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onNotesEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> eventNotesOrder(event)
            is NotesEvent.DeleteNote -> eventDeleteNote(event)
            is NotesEvent.RestoreNote -> eventRestoreNote()
            is NotesEvent.ToggleOrderSection -> _noteState.value = noteState.value.copy(
                isOrderSectionVisible = !noteState.value.isOrderSectionVisible
            )

        }
    }

    private fun eventNotesOrder(event: NotesEvent.Order) {
        if (noteState.value.noteOrder::class == event.noteOrder::class &&
            noteState.value.noteOrder.orderType == event.noteOrder.orderType
        ) {
            return
        }
        getNotes(event.noteOrder)
    }

    private fun eventDeleteNote(event: NotesEvent.DeleteNote) {
        viewModelScope.launch {
            noteUseCases.deleteNote(event.note)
            recentlyDeletedNote = event.note
        }
    }

    private fun eventRestoreNote() {
        viewModelScope.launch {
            noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
            recentlyDeletedNote = null
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder).onEach { notes ->
            _noteState.value = noteState.value.copy(
                notes = notes,
                noteOrder = noteOrder
            )
        }
            .launchIn(viewModelScope)
    }
}