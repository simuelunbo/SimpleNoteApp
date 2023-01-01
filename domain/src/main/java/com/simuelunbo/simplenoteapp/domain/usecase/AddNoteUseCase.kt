package com.simuelunbo.simplenoteapp.domain.usecase

import com.simuelunbo.simplenoteapp.domain.exception.InvalidNoteException
import com.simuelunbo.simplenoteapp.domain.model.Note
import com.simuelunbo.simplenoteapp.domain.repository.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("노트의 제목 칸을 비워서는 안됩니다.")
        }
        if(note.content.isBlank()) {
            throw InvalidNoteException("노트 내용 칸을 비워서는 안됩니다.")
        }
        repository.insertNote(note)
    }
}