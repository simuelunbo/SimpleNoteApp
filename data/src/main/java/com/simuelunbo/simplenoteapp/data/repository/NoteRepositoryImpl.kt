package com.simuelunbo.simplenoteapp.data.repository

import com.simuelunbo.simplenoteapp.data.local.dao.NoteDao
import com.simuelunbo.simplenoteapp.data.local.entity.NoteHistory
import com.simuelunbo.simplenoteapp.domain.model.Note
import com.simuelunbo.simplenoteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao
) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes().map { notes ->
            convertNotes(notes)
        }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)?.convertNoteHistoryToNote()
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(convertNoteHistory(note))
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(convertNoteHistory(note))
    }


    private fun convertNotes(notes: List<NoteHistory>): List<Note> {
        return notes.map { note ->
            note.convertNoteHistoryToNote()
        }
    }

    private fun convertNoteHistory(note: Note): NoteHistory =
        NoteHistory(
            title = note.title,
            content = note.content,
            timestamp = note.timestamp,
            color = note.color,
            id = note.id
        )

}