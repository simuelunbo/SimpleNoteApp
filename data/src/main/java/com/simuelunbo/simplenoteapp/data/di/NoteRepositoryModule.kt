package com.simuelunbo.simplenoteapp.data.di

import com.simuelunbo.simplenoteapp.data.local.database.NoteDatabase
import com.simuelunbo.simplenoteapp.data.repository.NoteRepositoryImpl
import com.simuelunbo.simplenoteapp.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NoteRepositoryModule {

    @Binds
    @Singleton
    fun bindNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }
}