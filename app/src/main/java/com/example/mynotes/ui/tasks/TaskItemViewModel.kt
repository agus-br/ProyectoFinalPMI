package com.example.mynotes.ui.tasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.data.NoteTask
import com.example.mynotes.data.NoteTaskRepository
import com.example.mynotes.data.NoteTaskType
import com.example.mynotes.ui.notes.AddEditNoteDestination
import com.example.mynotes.ui.notes.NoteDetails
import com.example.mynotes.ui.notes.NoteUiState
import com.example.mynotes.ui.notes.toNote
import com.example.mynotes.ui.notes.toNoteUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TaskItemViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: NoteTaskRepository
) : ViewModel() {

    suspend fun updateTask(task: NoteTask) {
        viewModelScope.launch {
            repository.updateNoteTask(task)
        }
    }

}
