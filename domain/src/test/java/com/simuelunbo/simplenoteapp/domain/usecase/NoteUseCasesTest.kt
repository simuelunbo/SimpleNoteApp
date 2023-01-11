package com.simuelunbo.simplenoteapp.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.simuelunbo.simplenoteapp.domain.model.Note
import com.simuelunbo.simplenoteapp.domain.repository.FakeNoteRepository
import com.simuelunbo.simplenoteapp.domain.util.NoteOrder
import com.simuelunbo.simplenoteapp.domain.util.OrderType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

@ExperimentalCoroutinesApi
class NoteUseCasesTest {
    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var useCase: NoteUseCases

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        useCase = NoteUseCases(
            GetNotesUseCase(fakeNoteRepository),
            DeleteNoteUseCase(fakeNoteRepository),
            AddNoteUseCase(fakeNoteRepository),
            GetNoteUseCase(fakeNoteRepository)
        )

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timestamp = index.toLong(),
                    color = index * 100,
                    id = index
                )
            )
        }
        runBlocking {
            notesToInsert.forEach { fakeNoteRepository.insertNote(it) }
        }

    }


    @Test
    @DisplayName("해당 id의 노트를 가져온다.")
    fun getNote() = runTest {
        // given
        val id = 1
        val note = Note("b", "b", 1L, 100, 1)

        // when
        val actual = useCase.getNote(id)

        // then
        assertThat(actual).isEqualTo(note)
    }

    @Test
    @DisplayName("노트를 삭제후 해당 노트를 가져올수 없다.")
    fun deleteNote() = runTest {
        // given
        val note = Note("b", "b", 1L, 100, 1)

        // when
        useCase.deleteNote(note)
        val actual = useCase.getNote(1)

        // then
        assertThat(actual).isEqualTo(null)
    }

    @Test
    @DisplayName("노트를 저장하면 해당 노트를 가져올수 있다.")
    fun insertNote() = runTest {
        // given
        val newNote = Note("test", "test1", 10000L, 10000, 9999)

        // when
        useCase.addNote(newNote)
        val actual = useCase.getNote(9999)

        // then
        assertThat(actual).isEqualTo(newNote)
    }

    @Test
    @DisplayName("제목 오름차순으로 가져온다.")
    fun orderNoteTitleAscending() = runTest {
        // when
        val notes = useCase.getNotes(NoteOrder.Title(OrderType.Ascending)).first()
        // then
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }
    }

    @Test
    @DisplayName("제목 내림차순으로 가져온다.")
    fun orderNoteTitleDescending() = runTest {
        // when
        val notes = useCase.getNotes(NoteOrder.Title(OrderType.Descending)).first()
        // then
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isGreaterThan(notes[i + 1].title)
        }
    }

    @Test
    @DisplayName("날짜 오름차순으로 가져온다.")
    fun orderNoteDateAscending() = runTest {
        // when
        val notes = useCase.getNotes(NoteOrder.Date(OrderType.Ascending)).first()
        // then
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].timestamp).isLessThan(notes[i + 1].timestamp)
        }
    }

    @Test
    @DisplayName("날짜 내림차순으로 가져온다.")
    fun orderNoteDateDescending() = runTest {
        // when
        val notes = useCase.getNotes(NoteOrder.Date(OrderType.Descending)).first()
        // then
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].timestamp).isGreaterThan(notes[i + 1].timestamp)
        }
    }

    @Test
    @DisplayName("color 오름차순으로 가져온다.")
    fun orderNoteColorAscending() = runTest {
        // when
        val notes = useCase.getNotes(NoteOrder.Color(OrderType.Ascending)).first()
        // then
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].color).isLessThan(notes[i + 1].color)
        }
    }

    @Test
    @DisplayName("color 내림차순으로 가져온다.")
    fun orderNoteColorDescending() = runTest {
        // when
        val notes = useCase.getNotes(NoteOrder.Color(OrderType.Descending)).first()
        // then
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].color).isGreaterThan(notes[i + 1].color)
        }
    }
}