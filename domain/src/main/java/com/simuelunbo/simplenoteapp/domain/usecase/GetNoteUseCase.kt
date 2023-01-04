package com.simuelunbo.simplenoteapp.domain.usecase

import com.simuelunbo.simplenoteapp.domain.model.Note
import com.simuelunbo.simplenoteapp.domain.repository.NoteRepository

class GetNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}