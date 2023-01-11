package com.simuelunbo.simplenoteapp.edit_note

sealed class UiEvent {
    data class ShowSnackBar(val message: String): UiEvent()
    object SaveNote: UiEvent()
}