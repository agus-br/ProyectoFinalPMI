package com.example.mynotes.ui.tasks

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

class TaskAddEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: NoteTaskRepository
) : ViewModel() {

    // Estado de los campos de la tarea
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    private val _completed = MutableStateFlow(false)
    val completed: StateFlow<Boolean> = _completed

    private var currentTask: NoteTask? = null

    // Cargar una tarea existente para edición
    fun loadTask(taskId: Int) {
        viewModelScope.launch {
            repository.getNoteTaskStream(taskId).first()?.let { task ->
                currentTask = task
                _title.value = task.title
                _description.value = task.description
                _completed.value = task.isCompleted
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

    // Actualizar el estado de completado
    fun onCompletedChange(isCompleted: Boolean) {
        _completed.value = isCompleted
    }

    // Guardar o actualizar una tarea en el repositorio
    fun saveTask() {
        viewModelScope.launch {
            val task = currentTask?.copy(
                title = _title.value,
                description = _description.value,
                isCompleted = _completed.value,
                type = NoteTaskType.TASK
            ) ?: NoteTask(
                title = _title.value,
                description = _description.value,
                isCompleted = _completed.value,
                type = NoteTaskType.TASK
            )
            if (currentTask == null) {
                repository.insertNoteTask(task)
            } else {
                repository.updateNoteTask(task)
            }
        }
    }

    // Eliminar la tarea actual en el repositorio
    fun deleteTask() {
        currentTask?.let { task ->
            viewModelScope.launch {
                repository.deleteNoteTask(task)
            }
        }
    }
}
