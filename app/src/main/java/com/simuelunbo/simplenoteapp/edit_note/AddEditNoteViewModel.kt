package com.simuelunbo.simplenoteapp.edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simuelunbo.simplenoteapp.domain.exception.InvalidNoteException
import com.simuelunbo.simplenoteapp.domain.model.Note
import com.simuelunbo.simplenoteapp.domain.usecase.NoteUseCases
import com.simuelunbo.simplenoteapp.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var currentNoteId: Int? = null

    private val _noteTitle = mutableStateOf<NoteTextFieldState>(
        NoteTextFieldState(
            hint = "제목을 입력하세요"
        )
    )
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf<NoteTextFieldState>(
        NoteTextFieldState(
            hint = "내용을 입력하세요"
        )
    )
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf<Int>(noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) initEditNote(noteId)
        }
    }


    fun onEditNoteEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> _noteTitle.value = noteTitle.value.copy(
                text = event.value
            )
            is AddEditNoteEvent.ChangeTitleFocus -> _noteTitle.value = noteTitle.value.copy(
                isHintVisible = !event.focusState.isFocused &&
                        noteTitle.value.text.isBlank()
            )
            is AddEditNoteEvent.EnteredContent -> _noteContent.value = noteContent.value.copy(
                text = event.value
            )
            is AddEditNoteEvent.ChangeContentFocus -> _noteContent.value = noteContent.value.copy(
                isHintVisible = !event.focusState.isFocused &&
                        noteContent.value.text.isBlank()
            )
            is AddEditNoteEvent.ChangeColor -> _noteColor.value = event.color
            is AddEditNoteEvent.SaveNote -> saveNote()
        }
    }

    private fun initEditNote(noteId: Int) {
        viewModelScope.launch {
            noteUseCases.getNote(noteId)?.also { note ->
                currentNoteId = note.id
                _noteTitle.value = noteTitle.value.copy(
                    text = note.title,
                    isHintVisible = false
                )
                _noteContent.value = noteContent.value.copy(
                    text = note.content,
                    isHintVisible = false
                )
            }
        }
    }

    private fun saveNote() {
        viewModelScope.launch {
            val msg = "노트 저장 할수 없습니다"
            runCatching {
                throw InvalidNoteException(msg)
            }.onSuccess {
                noteUseCase.addNote(
                    Note(
                        title = noteTitle.value.text,
                        content = noteContent.value.text,
                        timestamp = System.currentTimeMillis(),
                        color = noteColor.value,
                        id = currentNoteId
                    )
                )
            }.onFailure {
                _eventFlow.emit(
                    UiEvent.ShowSnackBar(msg)
                )
            }
        }
    }

}