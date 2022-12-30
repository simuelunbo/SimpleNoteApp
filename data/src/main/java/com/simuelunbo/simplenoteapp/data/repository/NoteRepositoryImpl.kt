package com.simuelunbo.simplenoteapp.data.repository

import com.simuelunbo.simplenoteapp.data.local.dao.NoteDao
import com.simuelunbo.simplenoteapp.domain.model.Note
import com.simuelunbo.simplenoteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

internal class NoteRepositoryImpl(
    private val dao: NoteDao
    ) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }
}