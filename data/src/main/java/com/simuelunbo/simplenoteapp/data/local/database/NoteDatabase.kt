package com.simuelunbo.simplenoteapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.simuelunbo.simplenoteapp.data.local.dao.NoteDao
import com.simuelunbo.simplenoteapp.data.local.entity.NoteHistory

@Database(entities = [NoteHistory::class], version = 1)
internal abstract class NoteDatabase : RoomDatabase(){
    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}