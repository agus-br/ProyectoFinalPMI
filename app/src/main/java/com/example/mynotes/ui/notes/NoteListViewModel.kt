package com.example.mynotes.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.data.NoteTask
import com.example.mynotes.data.NoteTaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteListViewModel(
    private val repository: NoteTaskRepository
) : ViewModel() {

    private val _notes = MutableStateFlow<List<NoteTask>>(emptyList())
    val notes: StateFlow<List<NoteTask>> = _notes.asStateFlow()

    init {
        fetchNotes()
    }

    private fun fetchNotes() {
        viewModelScope.launch {
            repository.getAllNoteTasksStream().collect { noteList ->
                // Filtrar los elementos que sean de tipo 'nota' (si tienes un indicador en NoteTask)
                _notes.value = noteList.filter { it.isNote()}
            }
        }
    }
}
