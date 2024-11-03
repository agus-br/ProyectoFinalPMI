package com.example.mynotes.ui.tasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mynotes.data.NoteTask
import com.example.mynotes.data.NoteTaskRepository
import com.example.mynotes.data.NoteTaskType

class TaskEntryViewModel(
    private val taskRepository: NoteTaskRepository
) : ViewModel() {

    // Estado actual de la interfaz de usuario para crear/editar una nota
    var taskUiState by mutableStateOf(TaskUiState())
        private set

    // Actualiza el estado de la UI de la nota y valida la entrada
    fun updateUiState(taskDetails: TaskDetails) {
        taskUiState = TaskUiState(taskDetails = taskDetails, isEntryValid = validateInput(taskDetails))
    }

    // Guarda la nota si es válida
    suspend fun saveNote() {
        if (validateInput()) {
            taskRepository.insertNoteTask(taskUiState.taskDetails.toTask())
        }
    }

    // Valida que el nombre y contenido de la nota no estén vacíos
    private fun validateInput(uiState: TaskDetails = taskUiState.taskDetails): Boolean {
        return uiState.title.isNotBlank() && uiState.description.isNotBlank()
    }
}

data class TaskUiState(
    val taskDetails: TaskDetails = TaskDetails(),
    val isEntryValid: Boolean = false
)

data class TaskDetails(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val type : NoteTaskType = NoteTaskType.TASK
)

// Convierte NoteDetails a Note
fun TaskDetails.toTask(): NoteTask = NoteTask(
    id = id,
    title = title,
    description = description,
    type = NoteTaskType.TASK
)

// Convierte NoteTask a NoteUiState
fun NoteTask.toTaskUiState(isEntryValid: Boolean = false): TaskUiState = TaskUiState(
    taskDetails = this.toTaskDetails(),
    isEntryValid = isEntryValid
)

// Convierte NoteTask a NoteDetails
fun NoteTask.toTaskDetails(): TaskDetails = TaskDetails(
    title = title,
    description = description,
    type = NoteTaskType.TASK
)
