package com.example.mynotes.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.data.NoteTask
import com.example.mynotes.data.NoteTaskRepository
import com.example.mynotes.data.NoteTaskType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteListViewModel(
    private val repository: NoteTaskRepository
) : ViewModel() {

    // StateFlow para mantener la lista de notas y actualizarla en la UI
    val notes: StateFlow<List<NoteTask>> = repository.getAllNoteTasksStream()
        .map { noteTasks ->
            noteTasks.filter { it.type == NoteTaskType.NOTE }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Método para eliminar una nota
    fun deleteNote(note: NoteTask) {
        viewModelScope.launch {
            repository.deleteNoteTask(note)
        }
    }
}