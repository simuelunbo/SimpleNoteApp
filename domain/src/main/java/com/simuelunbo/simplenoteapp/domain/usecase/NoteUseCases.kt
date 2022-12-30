package com.simuelunbo.simplenoteapp.domain.usecase

data class NoteUseCases(
    val getNotes: GetNotesUseCase,
    val deleteNote: DeleteNoteUseCase
)