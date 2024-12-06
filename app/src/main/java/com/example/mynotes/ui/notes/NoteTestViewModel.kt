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
        noteId?.let {
            if (it != 0) {
                isEditMode = true
                loadNoteTask(it)
                loadMediaFiles(it)
            }
        }
    }

    fun updateNoteTask(title: String, description: String) {
        noteTask = noteTask.copy(
            title = title,
            description = description
        )
    }

    private fun loadNoteTask(
        noteTaskId: Int
    ) {
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
        if (validateInput()) {
            try {
                val noteIdResult = if (isEditMode) {
                    repository.updateNoteTask(noteTask)
                    noteTask.id
                } else {
                    repository.insertNoteTask(noteTask)
                }

                if (!isEditMode) { // Solo inserta si NO es modo edición
                    _mediaFiles.value.forEach { mediaFile ->
                        repository.insertMediaFile(mediaFile.copy(noteTaskId = noteIdResult))
                    }
                }

                return noteIdResult
            } catch (e: Exception) {
                // Manejar la excepción, por ejemplo, mostrar un mensaje de error al usuario
                return 0 // o algún valor que indique un error
            }
        } else {
            // Manejar la validación fallida, por ejemplo, mostrar un mensaje al usuario
            return 0 // o algún valor que indique un error
        }
    }

    fun deleteNoteTask() {
        viewModelScope.launch {
            repository.deleteNoteTask(noteTask)
        }
    }


    // TODO MÉTODOS PARA LOS MEDIAFILES


    private val _mediaFiles = MutableStateFlow<List<MediaFile>>(emptyList())
    val mediaFiles: StateFlow<List<MediaFile>> = _mediaFiles

    private fun loadMediaFiles(noteTaskId: Int) {
        viewModelScope.launch {
            val files = repository.getMediaFilesByNoteTaskIdStream(noteTaskId)
                .filterNotNull()
                .first()
            _mediaFiles.value = files
        }
    }

    fun addMediaFile(
        filePath: String,
        mediaType: MediaType
    ) {
        viewModelScope.launch {
            val newMediaFile = MediaFile(
                noteTaskId = if (isEditMode) noteTask.id else 0,
                filePath = filePath,
                mediaType = mediaType
            )

            if (isEditMode) {
                val mediaFileId = repository.insertMediaFile(newMediaFile)
                _mediaFiles.value = _mediaFiles.value + newMediaFile.copy(id = mediaFileId)
            } else {
                _mediaFiles.value = _mediaFiles.value + newMediaFile
            }
        }
    }

    fun removeMediaFile(
        mediaFile: MediaFile
    ) {
        viewModelScope.launch {
            if (isEditMode) {
                repository.deleteMediaFile(mediaFile)
            }
            _mediaFiles.value = _mediaFiles.value - mediaFile
        }
    }


}
