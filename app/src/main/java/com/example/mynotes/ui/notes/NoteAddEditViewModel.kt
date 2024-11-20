package com.example.mynotes.ui.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.data.NoteTask
import com.example.mynotes.data.NoteTaskRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NoteAddEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: NoteTaskRepository
) : ViewModel() {

    var noteUiState by mutableStateOf(NoteUiState())
        private set

    private var currentNoteTask: NoteTask? = null

    private val noteId: Int? = savedStateHandle[AddEditNoteDestination.noteIdArg]

    private fun validateInput(uiState: NoteDetails = noteUiState.noteDetails): Boolean {
        return with(uiState) {
            title.isNotBlank() && description.isNotBlank()
        }
    }

    init {
        // Carga la nota existente si se proporciona un ID
        noteId?.let { loadNoteTask(it) }
    }

    // Cargar una nota existente para edición
    private fun loadNoteTask(noteTaskId: Int) {
        viewModelScope.launch {
            noteUiState = repository.getNoteTaskByIdStream(noteTaskId)
                .filterNotNull()
                .first()
                .toNoteUiState(true)
        }
    }

    fun updateUiState(noteDetails: NoteDetails) {
        noteUiState =
            NoteUiState(noteDetails = noteDetails, isEntryValid = validateInput(noteDetails))
    }

    suspend fun updateItem() {
        if (validateInput(noteUiState.noteDetails)) {
            repository.updateNoteTask(noteUiState.noteDetails.toNote())
        }
    }

    fun deleteNoteTask() {
        viewModelScope.launch {
            repository.deleteNoteTask(noteUiState.noteDetails.toNote())
        }
    }


    /*// Guardar o actualizar una nota en el repositorio
    fun saveNoteTask() {
        viewModelScope.launch {
            val noteTask = currentNoteTask?.copy(
                title = _noteUiState.value.title,
                description = _noteUiState.value.description,
                type = NoteTaskType.NOTE
            ) ?: NoteTask(
                title = _noteUiState.value.title,
                description = _noteUiState.value.description,
                type = NoteTaskType.NOTE
            )
            if (currentNoteTask == null) {
                repository.insertNoteTask(noteTask)
            } else {
                repository.updateNoteTask(noteTask)
            }
        }
    }

    // Eliminar la nota actual en el repositorio
    fun deleteNoteTask() {
        currentNoteTask?.let { noteTask ->
            viewModelScope.launch {
                repository.deleteNoteTask(noteTask)
            }
        }
    }*/

}