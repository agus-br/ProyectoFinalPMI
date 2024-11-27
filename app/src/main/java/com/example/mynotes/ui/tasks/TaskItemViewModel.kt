package com.example.mynotes.ui.tasks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.data.NoteTask
import com.example.mynotes.data.NoteTaskRepository
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
