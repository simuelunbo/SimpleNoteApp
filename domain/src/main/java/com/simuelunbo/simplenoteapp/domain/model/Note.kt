package com.simuelunbo.simplenoteapp.domain.model

data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val id: Int?
)
