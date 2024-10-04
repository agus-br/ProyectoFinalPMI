package com.example.mynotes.data.model

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val content: String,
    val dueDate: String?,  // La fecha límite es opcional
    val reminder: Long
)
