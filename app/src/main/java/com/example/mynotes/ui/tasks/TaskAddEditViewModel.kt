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

class TaskAddEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: NoteTaskRepository
) : ViewModel() {

    var taskUiState by mutableStateOf(TaskUiState())
        private set

    private var currentNoteTask: NoteTask? = null

    private val taskId: Int? = savedStateHandle[AddEditTaskDestination.taskIdArg]

    private fun validateInput(uiState: TaskDetails = taskUiState.taskDetails): Boolean {
        return with(uiState) {
            title.isNotBlank() && description.isNotBlank()
        }
    }

    init {
        // Carga la nota existente si se proporciona un ID
        taskId?.let { loadTask(it) }
    }

    // Cargar una nota existente para edición
    private fun loadTask(taskId: Int) {
        viewModelScope.launch {
            taskUiState = repository.getNoteTaskByIdStream(taskId)
                .filterNotNull()
                .first()
                .toTaskUiState(true)
        }
    }

    fun updateUiState(taskDetails: TaskDetails) {
        taskUiState =
            TaskUiState(taskDetails = taskDetails, isEntryValid = validateInput(taskDetails))
    }

    suspend fun updateItem() {
        if (validateInput(taskUiState.taskDetails)) {
            repository.updateNoteTask(taskUiState.taskDetails.toTask())
        }
    }

}
