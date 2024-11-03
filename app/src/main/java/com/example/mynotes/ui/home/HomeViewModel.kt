package com.example.mynotes.ui.home

import androidx.lifecycle.ViewModel
import com.example.mynotes.data.NoteTask
import kotlinx.coroutines.flow.StateFlow
import com.example.mynotes.data.NoteTaskRepository
import androidx.lifecycle.viewModelScope
import com.example.mynotes.data.NoteTaskType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel to retrieve all items in the Room database.
 */
class HomeViewModel(
    noteTaskRepository: NoteTaskRepository
): ViewModel() {

    val homeUiStateNotes: StateFlow<HomeUiState> =
        noteTaskRepository.getAllNoteTasksStream()
            .map { notes ->
                // Filtrar solo las notas
                val noteList = notes.filter { it.type == NoteTaskType.NOTE }
                HomeUiState(noteList)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    val homeUiStateTasks: StateFlow<HomeUiState> =
        noteTaskRepository.getAllNoteTasksStream()
            .map { task ->
                // Filtrar solo las notas
                val taskList = task.filter { it.type == NoteTaskType.TASK }
                HomeUiState(taskList)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Ui State for HomeScreen
 */
data class HomeUiState(
    val noteTaskList: List<NoteTask> = listOf()
)
