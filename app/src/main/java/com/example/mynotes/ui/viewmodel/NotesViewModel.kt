package com.example.mynotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.example.mynotes.data.model.Note

class NotesViewModel(
) : ViewModel() {

    // Estado para las notas
    private val _notes = mutableStateOf<List<Note>>(emptyList())
    val notes: State<List<Note>> = _notes

    // Función para agregar una nota
    fun addNote(note: Note) {
        _notes.value += note
    }

    // Función para editar una nota
    fun editNote(updatedNote: Note) {
        _notes.value = _notes.value.map { note ->
            if (note.id == updatedNote.id) updatedNote else note
        }
    }

    // Función para eliminar una nota
    fun deleteNote(noteId: String) {
        _notes.value = _notes.value.filter { it.id != noteId }
    }
}