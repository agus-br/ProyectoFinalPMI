package com.example.mynotes.ui.tasks

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
import com.example.mynotes.data.Reminder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddEditTaskViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: NoteTaskRepository
) : ViewModel() {

    // Estado actual de la nota
    var task by mutableStateOf(
        NoteTask(
            id = 0,
            title = "",
            description = "",
            createdDate = System.currentTimeMillis(),
            lastEditedDate = System.currentTimeMillis(),
            type = NoteTaskType.TASK
        )
    )
        private set

    private var isEditMode = false

    private val taskId: Int? = savedStateHandle[AddEditTaskDestination.taskIdArg]


    init {
        // Verificamos si estamos en modo edición (noteId != 0)
        taskId?.let {
            if (it != 0) {
                isEditMode = true
                loadNoteTask(it)  // Cargamos la nota para editar
            }
        }
    }

    fun updateNoteTask(title: String, description: String) {
        task = task.copy(
            title = title,
            description = description
        )
    }

    private fun loadNoteTask(noteTaskId: Int) {
        viewModelScope.launch {
            task = repository.getNoteTaskByIdStream(noteTaskId)
                .filterNotNull()
                .first()
        }
    }

    private fun validateInput(): Boolean {
        return task.title.isNotBlank() && task.description.isNotBlank()
    }


    suspend fun saveNoteTask(): Int {
        var taskIdResult = 0
        if (validateInput()) {
            if (isEditMode) {
                repository.updateNoteTask(task)  // Editamos la tarea
                taskIdResult = task.id
            } else {
                taskIdResult = repository.insertNoteTask(task).toInt()  // Creamos una nueva tarea
            }
        }
        return taskIdResult
    }

    fun deleteNoteTask() {
        viewModelScope.launch {
            repository.deleteNoteTask(task)
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

    fun addMediaFile(taskId: Int, filePath: String, mediaType: MediaType) {
        viewModelScope.launch {
            repository.insertMediaFile(
                MediaFile(
                    noteTaskId = taskId, // Usar el noteId recibido
                    filePath = filePath,
                    mediaType = mediaType
                )
            )
            loadMediaFiles(taskId) // Recargar la lista
        }
    }

    fun removeMediaFile(mediaFile: MediaFile) {
        viewModelScope.launch {
            repository.deleteMediaFile(mediaFile)
            loadMediaFiles(task.id) // Recargar la lista
        }
    }


    // MÉTODOS PARA EL MANEJO DE RECORDATORIOS

    // Flujo para observar los recordatorios asociados a la tarea
    private val _reminders = MutableStateFlow<List<Reminder>>(emptyList())
    val reminders: StateFlow<List<Reminder>> = _reminders

    /**
     * Cargar recordatorios asociados a una tarea.
     */
    fun loadReminders(noteTaskId: Int) {
        viewModelScope.launch {
            repository.getRemindersByNoteTaskIdStream(noteTaskId).collect { loadedReminders ->
                _reminders.value = loadedReminders
            }
        }
    }

    /**
     * Agregar un nuevo recordatorio.
     */
    fun addReminder(reminder: Reminder) {
        viewModelScope.launch {
            repository.insertReminder(reminder)
            loadReminders(reminder.noteTaskId) // Recargar los recordatorios después de insertar
        }
    }

    /**
     * Actualizar un recordatorio existente.
     */
    fun updateReminder(reminder: Reminder) {
        viewModelScope.launch {
            repository.updateReminder(reminder)
            loadReminders(reminder.noteTaskId) // Recargar los recordatorios después de actualizar
        }
    }

    /**
     * Eliminar un recordatorio.
     */
    fun removeReminder(reminder: Reminder) {
        viewModelScope.launch {
            repository.deleteReminder(reminder)
            loadReminders(reminder.noteTaskId) // Recargar los recordatorios después de eliminar
        }
    }


}
