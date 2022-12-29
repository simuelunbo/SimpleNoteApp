package com.simuelunbo.simplenoteapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.simuelunbo.simplenoteapp.domain.model.Note

@Entity
internal data class NoteHistory(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
) {
    fun convertNoteHistoryToNote(): Note = Note(title, content, timestamp, color, id)

}

class InvalidNoteException(message: String) : Exception(message)
