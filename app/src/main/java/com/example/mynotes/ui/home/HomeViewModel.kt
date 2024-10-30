package com.example.mynotes.ui.home

import androidx.lifecycle.ViewModel
import com.example.mynotes.data.NoteTask
import kotlinx.coroutines.flow.StateFlow
import com.example.mynotes.data.NoteTaskRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel to retrieve all items in the Room database.
 */
class HomeViewModel(noteTaskRepository: NoteTaskRepository): ViewModel() {

    val homeUiState: StateFlow<HomeUiState> =
        noteTaskRepository.getAllNoteTasksStream().map { HomeUiState(it) }
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
data class HomeUiState(val noteTaskList: List<NoteTask> = listOf())
