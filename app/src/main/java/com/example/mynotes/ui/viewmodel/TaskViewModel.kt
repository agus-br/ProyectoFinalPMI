package com.example.mynotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.example.mynotes.data.model.Task

class TasksViewModel : ViewModel() {

    // Lista observable de tareas
    private val _tasks = mutableStateOf<List<Task>>(emptyList())
    val tasks: State<List<Task>> = _tasks

    // Agregar una nueva tarea
    fun addTask(task: Task) {
        _tasks.value = _tasks.value + task
    }

    // Editar una tarea existente
    fun editTask(updatedTask: Task) {
        _tasks.value = _tasks.value.map { task ->
            if (task.id == updatedTask.id) updatedTask else task
        }
    }

    // Eliminar una tarea por ID
    fun deleteTask(taskId: String) {
        _tasks.value = _tasks.value.filter { it.id != taskId }
    }

    // Agregar recordatorios
    fun setReminder(taskId: String, reminderTime: Long) {
        _tasks.value = _tasks.value.map { task ->
            if (task.id == taskId) task.copy(reminder = reminderTime) else task
        }
    }

    // Agregar una fecha límite
    fun setDeadline(taskId: String, dueDate: String?) {
        _tasks.value = _tasks.value.map { task ->
            if (task.id == taskId) task.copy(dueDate = dueDate) else task
        }
    }
}
