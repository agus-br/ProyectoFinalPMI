package com.example.mynotes.ui.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mynotes.data.NoteTask
import com.example.mynotes.data.NoteTaskRepository
import com.example.mynotes.data.NoteTaskType

class NoteEntryViewModel(
    private val notesRepository: NoteTaskRepository
) : ViewModel() {

    // Estado actual de la interfaz de usuario para crear/editar una nota
    var noteUiState by mutableStateOf(NoteUiState())
        private set

    // Actualiza el estado de la UI de la nota y valida la entrada
    fun updateUiState(taskDetails: NoteDetails) {
        noteUiState = NoteUiState(noteDetails = taskDetails, isEntryValid = validateInput(taskDetails))
    }

    // Guarda la nota si es válida
    suspend fun saveNote() {
        if (validateInput()) {
            notesRepository.insertNoteTask(noteUiState.noteDetails.toNote())
        }
    }

    // Valida que el nombre y contenido de la nota no estén vacíos
    private fun validateInput(uiState: NoteDetails = noteUiState.noteDetails): Boolean {
        return uiState.title.isNotBlank() && uiState.description.isNotBlank()
    }
}

data class NoteUiState(
    val noteDetails: NoteDetails = NoteDetails(),
    val isEntryValid: Boolean = false
)

data class NoteDetails(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val type : NoteTaskType = NoteTaskType.NOTE
)

// Convierte NoteDetails a Note
fun NoteDetails.toNote(): NoteTask = NoteTask(
    id = id,
    title = title,
    description = description,
    type = NoteTaskType.NOTE
)

// Convierte NoteTask a NoteUiState
fun NoteTask.toNoteUiState(isEntryValid: Boolean = false): NoteUiState = NoteUiState(
    noteDetails = this.toNoteDetails(),
    isEntryValid = isEntryValid
)

// Convierte NoteTask a NoteDetails
fun NoteTask.toNoteDetails(): NoteDetails = NoteDetails(
    id = id,
    title = title,
    description = description,
    type = NoteTaskType.NOTE
)
