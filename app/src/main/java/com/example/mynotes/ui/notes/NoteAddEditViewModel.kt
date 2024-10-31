package com.example.mynotes.ui.notes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.data.NoteTask
import com.example.mynotes.data.NoteTaskRepository
import com.example.mynotes.data.NoteTaskType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NoteAddEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: NoteTaskRepository
) : ViewModel() {

    // Estado de los campos de la nota
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content

    private var currentNoteTask: NoteTask? = null

    // Cargar una nota existente para edición
    fun loadNoteTask(noteTaskId: Int) {
        viewModelScope.launch {
            repository.getNoteTaskStream(noteTaskId).first()?.let { noteTask ->
                currentNoteTask = noteTask
                _title.value = noteTask.title
                _description.value = noteTask.description
            }
        }
    }

    // Actualizar el estado del título
    fun onTitleChange(newTitle: String) {
        _title.value = newTitle
    }

    // Actualizar el estado de la descripción
    fun onDescriptionChange(newDescription: String) {
        _description.value = newDescription
    }

    // Actualizar el estado del contenido
    fun onContentChange(newContent: String) {
        _content.value = newContent
    }

    // Guardar o actualizar una nota en el repositorio
    fun saveNoteTask() {
        viewModelScope.launch {
            val noteTask = currentNoteTask?.copy(
                title = _title.value,
                description = _description.value,
                type = NoteTaskType.NOTE
            ) ?: NoteTask(
                title = _title.value,
                description = _description.value,
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
    }
}
