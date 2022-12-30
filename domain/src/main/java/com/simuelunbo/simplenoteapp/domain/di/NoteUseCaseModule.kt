package com.simuelunbo.simplenoteapp.domain.di

import com.simuelunbo.simplenoteapp.domain.repository.NoteRepository
import com.simuelunbo.simplenoteapp.domain.usecase.DeleteNoteUseCase
import com.simuelunbo.simplenoteapp.domain.usecase.GetNotesUseCase
import com.simuelunbo.simplenoteapp.domain.usecase.NoteUseCases
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NoteUseCaseModule {

    @Binds
    @Singleton
    fun bindsNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotesUseCase(repository),
            deleteNote = DeleteNoteUseCase(repository)
        )
    }
}