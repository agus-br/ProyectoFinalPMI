package com.example.mynotes.ui.tasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.data.NoteTaskRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first

class TaskAddEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NoteTaskRepository
) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var noteUiState by mutableStateOf(TaskUiState())
        private set

    private val noteId: Int = checkNotNull(savedStateHandle[TaskEditDestination.noteIdArg])

    private fun validateInput(uiState: TaskDetails = TaskUiState.noteDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
    }

    init {
        viewModelScope.launch {
            noteUiState = notesRepository.getTaskStream(noteId)
                .filterNotNull()
                .first()
                .toItemUiState(true)
        }
    }

    fun updateUiState(noteDetails: NoteDetails) {
        noteUiState =
            NoteUiState(noteDetails = noteDetails, isEntryValid = validateInput(noteDetails))
    }

    suspend fun updateItem() {
        if (validateInput(noteUiState.itemDetails)) {
            notesRepository.updateItem(noteUiState.itemDetails.toItem())
        }
    }
}
