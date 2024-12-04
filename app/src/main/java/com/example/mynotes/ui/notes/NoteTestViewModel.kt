package com.example.mynotes.ui.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.data.MediaFile
import com.example.mynotes.data.MediaType
import com.example.mynotes.data.NoteTask
import com.example.mynotes.data.NoteTaskRepository
import com.example.mynotes.data.NoteTaskType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NoteTestViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: NoteTaskRepository
) : ViewModel() {

    // Estado actual de la nota
    var noteTask by mutableStateOf(
        NoteTask(
            id = 0,
            title = "",
            description = "",
            createdDate = System.currentTimeMillis(),
            lastEditedDate = System.currentTimeMillis(),
            type = NoteTaskType.NOTE
        )
    )
        private set

    private var isEditMode = false

    private val noteId: Int? = savedStateHandle[NoteTestDestination.noteIdArg]


    init {
        // Verificamos si estamos en modo edición (noteId != 0)
        noteId?.let {
            if (it != 0) {
                isEditMode = true
                loadNoteTask(it)  // Cargamos la nota para editar
            }
        }
    }

    fun updateNoteTask(title: String, description: String) {
        noteTask = noteTask.copy(
            title = title,
            description = description
        )
    }

    private fun loadNoteTask(noteTaskId: Int) {
        viewModelScope.launch {
            noteTask = repository.getNoteTaskByIdStream(noteTaskId)
                .filterNotNull()
                .first()
        }
    }

    private fun validateInput(): Boolean {
        return noteTask.title.isNotBlank() && noteTask.description.isNotBlank()
    }


    suspend fun saveNoteTask(): Int {
        var noteIdResult = 0
        if (validateInput()) {
            if (isEditMode) {
                repository.updateNoteTask(noteTask)  // Editamos la nota
                noteIdResult = noteTask.id
            } else {
                noteIdResult = repository.insertNoteTask(noteTask).toInt()  // Creamos una nueva nota
            }
        }
        return noteIdResult
    }

    fun deleteNoteTask() {
        viewModelScope.launch {
            repository.deleteNoteTask(noteTask)
        }
    }


    //  MÉTODOS PARA EL MANEJO DE MULTIMEDIA

    // Flujos para observar los archivos multimedia
    private val _mediaFiles = MutableStateFlow<List<MediaFile>>(emptyList())

    val mediaFiles: StateFlow<List<MediaFile>> = _mediaFiles

    fun loadMediaFiles(noteTaskId: Int) {
        viewModelScope.launch {
            repository.getMediaFilesByNoteTaskIdStream(noteTaskId).collect { files ->
                _mediaFiles.value = files
            }
        }
    }

    fun addMediaFile(noteId: Int, filePath: String, mediaType: MediaType) {
        viewModelScope.launch {
            repository.insertMediaFile(
                MediaFile(
                    noteTaskId = noteId, // Usar el noteId recibido
                    filePath = filePath,
                    mediaType = mediaType
                )
            )
            loadMediaFiles(noteId) // Recargar la lista
        }
    }

    fun removeMediaFile(mediaFile: MediaFile) {
        viewModelScope.launch {
            repository.deleteMediaFile(mediaFile)
            loadMediaFiles(noteTask.id) // Recargar la lista
        }
    }

}
