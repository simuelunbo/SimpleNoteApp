package com.simuelunbo.simplenoteapp.data.local.dao

import androidx.room.*
import com.simuelunbo.simplenoteapp.data.local.entity.NoteHistory
import kotlinx.coroutines.flow.Flow

@Dao
internal interface NoteDao {
    @Query("SELECT * FROM NoteHistory")
    fun getNotes(): Flow<List<NoteHistory>>

    @Query("SELECT * FROM NoteHistory WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteHistory?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteHistory)

    @Delete
    suspend fun deleteNote(note: NoteHistory)
}