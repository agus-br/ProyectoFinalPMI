package com.example.mynotes.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.data.NoteTask
import com.example.mynotes.data.NoteTaskRepository
import com.example.mynotes.data.NoteTaskType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val repository: NoteTaskRepository
) : ViewModel() {

    // StateFlow para mantener la lista de tareas
    val tasks: StateFlow<List<NoteTask>> = repository.getAllNoteTasksStream()
        .map { noteTasks ->
            noteTasks.filter { it.type == NoteTaskType.TASK }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Método para eliminar una tarea
    fun deleteTask(task: NoteTask) {
        viewModelScope.launch {
            repository.deleteNoteTask(task)
        }
    }

    // Método para marcar una tarea como completada
    fun completeTask(task: NoteTask) {
        val updatedTask = task.copy(isCompleted = true)
        viewModelScope.launch {
            repository.updateNoteTask(updatedTask)
        }
    }
}