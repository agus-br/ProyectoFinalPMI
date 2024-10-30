package com.example.mynotes.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.data.NoteTask
import com.example.mynotes.data.NoteTaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TaskListViewModel(private val repository: NoteTaskRepository) : ViewModel() {
    private val _tasks = MutableStateFlow<List<NoteTask>>(emptyList())
    val tasks: StateFlow<List<NoteTask>> = _tasks

    init {
        fetchTasks()
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            repository.getAllNoteTasksStream().collect { noteList ->
                // Filtrar los elementos que tienen el tipo TASK
                _tasks.value = noteList.filter { it.isTask() }
            }
        }
    }
}